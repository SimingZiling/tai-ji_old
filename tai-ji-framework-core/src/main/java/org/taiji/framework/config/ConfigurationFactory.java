package org.taiji.framework.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.config.exception.ApplicationConfigurationException;

import java.util.ServiceLoader;

/**
 * 配置工厂
 */
public class ConfigurationFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 配置分发
    public void distribution() throws ApplicationConfigurationException {
        logger.info("开始分发配置");
        // 配置加载完成后进行配置分发，将应用程序配置分发到模块中
        ServiceLoader<Configuration> serviceLoader = ServiceLoader.load(Configuration.class);
        for (Configuration configuration : serviceLoader){
            // 构建配置，根据配置实现类进行配置的构建
            configuration.buildConfiguration();
        }
    }

}
