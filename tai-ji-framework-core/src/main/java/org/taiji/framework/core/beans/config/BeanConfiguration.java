package org.taiji.framework.core.beans.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.beans.exception.BeanException;
import org.taiji.framework.core.confog.ApplicationConfiguration;
import org.taiji.framework.core.confog.Configuration;

/**
 * Bean配置文件,实现Configuration接口进行请求分发
 */
public class BeanConfiguration implements Configuration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 扫描包
     */
    private static String[] scanPackages;

    public static String[] getScanPackages() {
        return scanPackages;
    }

    @Override
    public void distribution() throws BeanException {
        logger.info("配置信息分发至Bean配置");
        String scanPackagesName = "scanPackages";
        if(!ApplicationConfiguration.containsConfig(scanPackagesName)){
            throw new BeanException(scanPackagesName+"配置不存在或名称不正确！");
        }
        scanPackages = String.valueOf(ApplicationConfiguration.getApplicationConfig(scanPackagesName)).split(",");
    }
}
