package org.taiji.framework.beans.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.config.ApplicationConfiguration;
import org.taiji.framework.config.Configuration;
import org.taiji.framework.config.exception.ApplicationConfigurationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean配置文件,实现Configuration接口进行请求分发
 */
public class BeanConfiguration implements Configuration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final List<String> scanPackages = new ArrayList<>();

    public static List<String> getScanPackages() {
        return scanPackages;
    }

    public static void setScanPackages(String scanPackage) {
        scanPackages.add(scanPackage);
    }

    @Override
    public void buildConfiguration() throws ApplicationConfigurationException {
        logger.info("开始构建Bean配置");
        String scanPackagesName = "taiji.scanPackages";
        for (String s : String.valueOf(ApplicationConfiguration.getConfig(scanPackagesName,true)).split(",")) {
            setScanPackages(s);
        }
    }
}
