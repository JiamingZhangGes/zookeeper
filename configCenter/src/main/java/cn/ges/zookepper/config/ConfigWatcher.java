package cn.ges.zookepper.config;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ${gespent} on 2017/10/29.
 * E-mail：jiamingzhangges@foxmail.com
 */
public class ConfigWatcher {
    /**
     * 使用指定类初始化日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigWatcher.class);

    private ZkClient client;

    //监听器
    private ConfigListener configListener;
    //操作配置接口
    private ConfigCenter configCenter;
    //构造函数
    public ConfigWatcher(ZkClient client, ConfigCenter configCenter) {
        this.client = client;
        this.configCenter = configCenter;
        //初始化监听器
        this.initConfigCenter();
    }
    private void initConfigCenter(){
        //初始化监听器
        configListener = new ConfigListener();
        configListener.setConfigCenter(configCenter);
    }

    public void watcher(String key) throws InterruptedException {
        //执行订阅command节点数据变化和servers节点的列表变化
        client.subscribeDataChanges(key, configListener);
        client.subscribeChildChanges(key, configListener);

    }


}
