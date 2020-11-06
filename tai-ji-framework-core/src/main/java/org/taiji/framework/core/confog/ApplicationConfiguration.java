package org.taiji.framework.core.confog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.exception.TaiJiFrameworkCoreException;
import org.taiji.framework.core.module.Module;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 应用程序配置文件，应用程序全局配置application.properties
 */
public class ApplicationConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置文件解析
     */
    private final Properties properties = new Properties();

    private String classPath = "classPath";

    /**
     * 构造方法，默认添加classPath
     */
    public ApplicationConfiguration() {
        // 如果classPath路径不存在则获取classpath路径
        if(!applicationConfig.containsKey(classPath)) {
            applicationConfig.put(classPath, Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        }
    }

    /**
     * 初始化配置文件
     */
    public void initConfiguration(){
        logger.info("开始加载配置文件");
        // 读取文件流
        if(applicationConfig.containsKey(classPath)){
            applicationConfig.put(classPath, Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        }
        // try-with-resources 在try关键字后面的( )里new一些需要自动关闭的资源
        try (InputStream inputStream = new FileInputStream(getApplicationConfig(classPath)+"application.properties")){
            properties.load(inputStream);
            applicationConfig.putAll(properties);
        } catch (FileNotFoundException e) {
            logger.error("application文件不存在！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 配置加载完成后进行配置分发，将应用程序配置分发到模块中
        ServiceLoader<Configuration> serviceLoader = ServiceLoader.load(Configuration.class);
        for (Configuration configuration : serviceLoader){
            try {
                configuration.distribution();
            } catch (TaiJiFrameworkCoreException e) {
                e.printStackTrace();
                // 当配置文件获取异常时，停止程序
                System.exit(1);
            }
        }
    }

    /**
     * 配置信息集合
     */
    private static final Map<Object, Object> applicationConfig = new HashMap<>();

    /**
     * 获取配置信息
     * @param name 配置名称
     * @return 配置信息
     */
    public static Object getApplicationConfig(String name) {
        return applicationConfig.get(name);
    }

    /**
     * 是否包含该配置
     * @param name 配置名称
     * @return 配置信息
     */
    public static boolean containsConfig(String name){
        return applicationConfig.containsKey(name);
    }
}
