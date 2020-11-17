package org.shaoyin.orm.session.impl;

import org.shaoyin.orm.config.Configuration;
import org.shaoyin.orm.exception.DatabaseConfigException;
import org.shaoyin.orm.pool.DefaultDataSource;
import org.shaoyin.orm.session.SqlSession;
import org.shaoyin.orm.session.SqlSessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * 默认会话Sql会话工厂
 * 负责加载配置文件，生产产生会话
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Configuration configuration = new Configuration();

    public DefaultSqlSessionFactory() throws DatabaseConfigException {
        // 加载配置文件
        logger.info("开始加载数据库配置文件");
        try(InputStream inputStream = new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()+"database.properties")) {
            //创建Properties对象用于读取**.properties文件
            Properties properties = new Properties();
            properties.load(inputStream);
            // 构建配置信息
            configuration.setConfiguration(new Configuration.Builder()
                    .addDriverClass(String.valueOf(properties.get("driverClass")))
                    .addPassword(String.valueOf(properties.get("password")))
                    .addUrl(String.valueOf(properties.get("url")))
                    .addUser(String.valueOf(properties.get("user")))
                    .build()
            );
        } catch (FileNotFoundException e) {
            logger.error("database文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DefaultSqlSessionFactory(Configuration configuration){
        configuration.setConfiguration(configuration);
    }

    public DefaultSqlSessionFactory(String driverClass,String url,String user,String password){
        // 构建配置信息
        try {
            configuration.setConfiguration(new Configuration.Builder()
                    .addDriverClass(String.valueOf(driverClass))
                    .addPassword(String.valueOf(password))
                    .addUrl(String.valueOf(url))
                    .addUser(String.valueOf(user))
                    .build());
        } catch (DatabaseConfigException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SqlSession openSqlSession() {
        DataSource dataSource = new DefaultDataSource(configuration);
        return new DefaultSqlSession(dataSource);
    }
}
