package org.shaoyin.database.config;

import org.shaoyin.database.exception.DatabaseException;
import org.shaoyin.database.jdbc.config.DatabaseConfiguration;
import org.shaoyin.database.jdbc.pool.config.ConnectionPoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.beans.config.BeanConfiguration;
import org.taiji.framework.config.Configuration;
import org.taiji.framework.config.exception.ApplicationConfigurationException;
import org.taiji.framework.config.ApplicationConfiguration;

public class DatabaseForTaiJiConfiguration implements Configuration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 加载数据库配置
     */
    private void loudDatabaseConfiguration() throws ApplicationConfigurationException {
        DatabaseConfiguration.setDriverClass(getConfiguration("shaoyin.driverClass",true));
        DatabaseConfiguration.setUrl(getConfiguration("shaoyin.url",true));
        DatabaseConfiguration.setUser(getConfiguration("shaoyin.user",true));
        DatabaseConfiguration.setPassword(getConfiguration("shaoyin.password",true));
    }

    /**
     * 加载连接池配置
     */
    private void loudConnectionPoolConfiguration() throws ApplicationConfigurationException {
        String maxConnection = getConfiguration("shaoyin.maxConnection",false);
        if(maxConnection != null) {
            ConnectionPoolConfiguration.setConnectionMaxNumber(Integer.parseInt(maxConnection));
        }
        String minConnection = getConfiguration("shaoyin.minConnection",false);
        if(minConnection != null) {
            ConnectionPoolConfiguration.setConnectionMinNumber(Integer.parseInt(minConnection));
        }
    }

    /**
     * 加载数据库配置
     * @param configName 配置名称
     * @return 配置参数
     */
    private String getConfiguration(String configName,boolean isException) throws ApplicationConfigurationException {
        Object config = ApplicationConfiguration.getConfig(configName,isException);
        if(config == null){
            return null;
        }
        return String.valueOf(ApplicationConfiguration.getConfig(configName,isException));
    }

    @Override
    public void buildConfiguration() throws ApplicationConfigurationException {
        logger.info("配置信息分发至ORM配置");
        // 添加指定扫描路径
        BeanConfiguration.setScanPackages("org.shaoyin.database.bean");
        // 加载数据库配置
        loudDatabaseConfiguration();
        // 加载连接池配置
        loudConnectionPoolConfiguration();
    }
}