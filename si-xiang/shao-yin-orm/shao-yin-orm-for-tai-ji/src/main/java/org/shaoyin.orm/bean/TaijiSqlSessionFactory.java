package org.shaoyin.orm.bean;

import org.shaoyin.orm.config.Configuration;
import org.shaoyin.orm.exception.DatabaseConfigException;
import org.shaoyin.orm.session.SqlSessionFactory;
import org.shaoyin.orm.session.impl.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.beans.annotation.Bean;
import org.taiji.framework.config.ApplicationConfiguration;
import org.taiji.framework.config.exception.ApplicationConfigurationException;


@Bean
public class TaijiSqlSessionFactory extends DefaultSqlSessionFactory implements SqlSessionFactory {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Configuration configuration = new Configuration();

    public TaijiSqlSessionFactory() throws DatabaseConfigException {
        // 加载配置文件
        logger.info("开始加载数据库配置");
        try {
            configuration.setConfiguration(new Configuration.Builder()
                        .addDriverClass(String.valueOf(ApplicationConfiguration.getConfig("shaoyin.driverClass",true)))
                        .addPassword(String.valueOf(ApplicationConfiguration.getConfig("shaoyin.password",true)))
                        .addUrl(String.valueOf(ApplicationConfiguration.getConfig("shaoyin.url",true)))
                        .addUser(String.valueOf(ApplicationConfiguration.getConfig("shaoyin.user",true)))
                        .build());
        } catch (ApplicationConfigurationException e) {
            throw new DatabaseConfigException(e.getLocalizedMessage());
        }
    }

}
