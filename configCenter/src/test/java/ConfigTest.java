import cn.ges.zookepper.config.ConfigCenterImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by ${gespent} on 2017/10/29.
 * E-mail：jiamingzhangges@foxmail.com
 */
public class ConfigTest {
    /**
     * 使用指定类初始化日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ConfigTest.class);

    @Test
    //初始化节点
    public  void ConfigAddTest() throws InterruptedException {
        ConfigCenterImpl yard = new ConfigCenterImpl("192.168.25.128:2181");
        yard.add("testKey1", "1");
        yard.add("testKey2", "2");
        yard.add("testKey3", "3");
        yard.add("testKey4", "4");
        yard.add("testKey5", "5");
        yard.add("testKey6", "6");

        // junit测试时，防止线程退出
        while (true) {
            Thread.sleep(5);
        }
    }
    @Test
    //获取一个
    public void  ConfigGetTest() {
        ConfigCenterImpl yard = new ConfigCenterImpl("192.168.25.128:2181");
        logger.info("value is===>"+yard.get("testKey1"));
        logger.info("value is===>"+yard.get("testKey2"));
        logger.info("value is===>"+yard.get("testKey3"));
        logger.info("value is===>"+yard.get("testKey4"));
        logger.info("value is===>"+yard.get("testKey5"));
        logger.info("value is===>"+yard.get("testKey6"));
    }
    @Test
    //获取所有
    public void  ConfigGetAllTest() throws InterruptedException {
        ConfigCenterImpl yard = new ConfigCenterImpl("192.168.25.128:2181");
        Map<String, String> all = yard.getAll();
        System.out.println(all);
    }
    @Test
    //更新
    public void  ConfigUpdateTest() throws InterruptedException {
        ConfigCenterImpl yard = new ConfigCenterImpl("192.168.25.128:2181");
        yard.update("testKey6", "testKey6");
    }
    @Test
    //删除
    public  void ConfigDelTest() throws InterruptedException {
        ConfigCenterImpl yard = new ConfigCenterImpl("192.168.25.128:2181");
        yard.delete("testKey1");
        yard.delete("testKey2");
        yard.delete("testKey3");
        yard.delete("testKey4");
        yard.delete("testKey5");
        yard.delete("testKey6");
    }
}

