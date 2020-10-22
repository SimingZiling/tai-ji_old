package org.dao.framework.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 配置
 */
public class Configuration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String classPath = "classPath";

    /**
     * 构造方法，默认添加classPath
     */
    public Configuration() {
        if(applicationConfig.size() == 0) {
            applicationConfig.put(classPath, Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        }
    }

    /**
     *  创建Properties对象用于读取**.properties文件
     */
    private final Properties properties = new Properties();

    /**
     * 配置信息集合
     */
    private static final Map<Object, Object> applicationConfig = new HashMap<>();

    /**
     * 获取配置信息
     * @param configName 配置名称
     * @return 配置信息
     */
    public static Object getConfig(String  configName){
        return applicationConfig.get(configName);
    }

    /**
     * 验证配置信息是否存在
     * @param configName 配置名称
     * @return 是否存在
     */
    public static boolean verifyConfig(String configName){
        return applicationConfig.containsKey(configName);
    }

    /**
     * 加载配置文件
     */
    public void doLoadApplicationConfig(){

        logger.info("开始接在配置文件");
        // 读取文件流
        // try-with-resources 在try关键字后面的( )里new一些需要自动关闭的资源
        if(!verifyConfig(classPath)){
            applicationConfig.put(classPath, Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        }
        try (InputStream inputStream = new FileInputStream(getConfig(classPath)+"application.properties")){
            properties.load(inputStream);
            applicationConfig.putAll(properties);
        } catch (FileNotFoundException e) {
            logger.error("application文件不存在！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
