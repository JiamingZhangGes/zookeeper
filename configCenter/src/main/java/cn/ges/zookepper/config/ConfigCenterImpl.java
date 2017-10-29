package cn.ges.zookepper.config;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${gespent} on 2017/10/29.
 * E-mail：jiamingzhangges@foxmail.com
 */
public class ConfigCenterImpl implements ConfigCenter{
    /**
     * 使用指定类初始化日志对象
     * 在日志输出的时候，可以打印出日志信息所在类
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigCenterImpl.class);
    /**
     * 存储配置内容的对象，volatile保证原子性、不使用缓存
     */
    private volatile Map<String, String> configProperties = new HashMap<String, String>();

    private ZkClient client;

    private ConfigWatcher configWatcher;

    //构造函数
    public ConfigCenterImpl(String serverstring) {
        this.client = new ZkClient(serverstring);
        //初始化watcher
        configWatcher = new ConfigWatcher(client,this);
        //初始化配置
        this.init();
    }

    /**
     * 加载配置到内存
     */
    public void init() {
        //判断指定节点是否存在
        if(!client.exists(root)){
            //如果不存在该节点,创建持久化节点
            client.createPersistent(root);
        }
        //保存配置的map如果为null
        if (configProperties == null) {
            //初始化map
            logger.info("start to init configProperties");
            configProperties = this.getAll();
            logger.info("init configProperties over");
        }
    }

    private String contactKey(String key){
        //连接地址到结尾
        return root.concat("/").concat(key);
    }



    /**
     * 增加节点
     * @param key
     * @param value
     */
    public void add(String key, String value) throws InterruptedException {
        //构造子节点路径
        String contactKey = this.contactKey(key);
        //创建节点
        this.client.createPersistent(contactKey, value);
        //监控新的节点
        configWatcher.watcher(contactKey);
    }

    /**
     * 更新节点
     * @param key
     * @param value
     */
    public void update(String key, String value) throws InterruptedException {
        String contactKey = this.contactKey(key);
        //修改指定节点的值
        this.client.writeData(contactKey, value);
        //监控节点
        configWatcher.watcher(contactKey);
    }

    public void delete(String key) {
        String contactKey = this.contactKey(key);
        this.client.delete(contactKey);
    }

    public String  get(String key) {
        if(this.configProperties.get(key) == null){
            String contactKey = this.contactKey(key);
            if(!this.client.exists(contactKey)){
                return null;
            }
            return this.client.readData(contactKey);
        }
        return configProperties.get(key);
    }
    public void reload() {
        List<String> yardList = this.client.getChildren(root);
        Map<String, String> currentYardProperties = new HashMap<String, String>();
        for(String yard : yardList){
            String value = this.client.readData(this.contactKey(yard));
            currentYardProperties.put(yard, value);
        }
        configProperties = currentYardProperties;
    }

    public Map<String, String> getAll() {
        //如果configProperties有值，直接返回,否则重新获取
            if(configProperties.size()!=0){
                return configProperties;
            }
            List<String> configList = this.client.getChildren(root);
            Map<String, String> currentYardProperties = new HashMap<String, String>();
            for(String key : configList){
                String contactKey = this.contactKey(key);
                String value = this.client.readData(contactKey);
//                String key = config.substring(config.indexOf("/")+1);
                currentYardProperties.put(key, value);
            }
            return currentYardProperties ;
        }

}
