package org.taiji.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.config.exception.ApplicationConfigurationException;

import java.io.*;
import java.util.*;

/**
 * 应用程序配置
 */
public class ApplicationConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置文件解析
     */
    private final Properties properties = new Properties();

    /**
     * 配置Map集合
     */
    private static Map<Object, Object> configurationMap = new HashMap<Object, Object>();

    private static final String classPath = "classPath";

    public ApplicationConfiguration(){
        // 如果配置文件中不存在classPath则添加classPath
        if(!configurationMap.containsKey(classPath)){
            setConfig(classPath,Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        }
    }

    /**
     * 设置配置
     * @param configName 配置名称
     * @param configValue 配置值
     */
    public static void setConfig(String configName,Object configValue) {
        // 当配置名称已经存在时，不设置配置值
        if(!configurationMap.containsKey(classPath)) {
            configurationMap.put(configName, configValue);
        }
    }

    /**
     * 通过配置名称获取配置
     * @param configName 配置名称
     * @return 获取配置
     */
    public static Object getConfig(String configName,boolean isException) throws ApplicationConfigurationException {
        if(isException && !configurationMap.containsKey(configName)){
            throw new ApplicationConfigurationException(configName+"配置不存在或者名称不正确！");
        }
        return configurationMap.get(configName);
    }

    /**
     * 执行加载配置
     */
    public void doLoudConfiguration(){
        logger.info("开始加载配置文件");
        try {
            File[] files = new File(String.valueOf(ApplicationConfiguration.getConfig(classPath,true))).listFiles();
            if(files == null || files.length <= 0){
                throw new ApplicationConfigurationException(classPath+" Class地址不存在！");
            }
            for (File file : files){
                // 获取文件类型，由于配置文件中properties文件比较提特殊，无法获取到文件类型，所以直接获取文件后缀。
                String fileType = file.getName().substring(file.getName().lastIndexOf("."));
                if(fileType.equals(".properties")){
                    // TODO 暂不实现多配置文件加载
                    if(file.getName().equals("application.properties")) {
                        doLoudPropertiesConfig(file);
                    }
                }
            }
        } catch (ApplicationConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行加载Properties配置
     * @param file 配置文件
     */
    private void doLoudPropertiesConfig(File file){
        try (InputStream inputStream = new FileInputStream(file)){
            properties.load(inputStream);
            configurationMap.putAll(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
