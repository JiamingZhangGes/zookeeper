package cn.ges.zookepper.config;

import java.util.Map;

/**
 * Created by ${gespent} on 2017/10/29.
 * E-mail：jiamingzhangges@foxmail.com
 * 定义需要的接口
 */
public interface ConfigCenter {
    /**
     * 配置根节点
     */
    static  String root = "/yard";

    /**
     * 初始化配置
     */
    public void init();

    /**
     * 重新加载配置
     */
    public void reload();

    /**
     * 添加配置
     * @param key
     * @param value
     */
    public void add(String key, String value) throws InterruptedException;

    /**
     * 更新配置
     * @param key
     * @param value
     */
    public void update(String key, String value) throws InterruptedException;

    /**
     *  删除配置
     * @param key
     */
    public void delete(String key);

    /**
     *  获取配置
     * @param key
     */
    public String  get(String key);

    /**
     *  获取全部配置
     */
    public Map<String, String> getAll();




}
