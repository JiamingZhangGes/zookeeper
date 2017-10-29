package cn.ges.zookepper.config;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ${gespent} on 2017/10/29.
 * E-mail：jiamingzhangges@foxmail.com
 * 需要外部注入configCenter
 * 监听器
 * IZkDataListener：监听指定znode数据变化
 * IZkChildListener:监听子节点数据变化
 */
public class ConfigListener implements IZkDataListener,IZkChildListener {
    /**
     * 使用指定类初始化日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigListener.class);

    //操作配置接口
    private ConfigCenter configCenter;

    //需要外部注入configCenter
    public void setConfigCenter(ConfigCenter configCenter) {
        this.configCenter = configCenter;
    }

    /**

     * 监听子节点数据变化
     * @param parentPath
     * @param currentChilds
     * @throws Exception
     */
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

        if (currentChilds == null) {
            //如果currentChilds为null，说明该操作是删除操作，只需要handleDataDeleted处理一次即可
            //打印信息
            logger.info("data {} ChildChange,but not start reload configProperties,because currentChilds is null",parentPath);
            return;
        }
        //打印信息
        logger.info("data {} ChildChange,start reload configProperties",parentPath);
        //重新载入配置
        configCenter.reload();

    }

    /**
     * 监听指定znode数据改变
     * @param dataPath
     * @param data
     * @throws Exception
     */
    public void handleDataChange(String dataPath, Object data) throws Exception {
        //打印信息
        logger.info("data {} change,start reload configProperties",dataPath);
        //重新载入配置
        configCenter.reload();
    }

    /**
     * 监听指定znode数据删除
     * @param dataPath
     * @throws Exception
     */
    public void handleDataDeleted(String dataPath) throws Exception {
        //打印信息
        logger.info("data {} delete,start reload configProperties",dataPath);
        //重新载入配置
        configCenter.reload();
    }
}
