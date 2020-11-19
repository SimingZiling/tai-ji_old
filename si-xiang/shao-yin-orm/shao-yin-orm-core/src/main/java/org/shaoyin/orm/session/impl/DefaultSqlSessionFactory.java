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
 * 默认Sql会话工厂
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 配置信息对象 */
    private final Configuration configuration = new Configuration();

    /**
     * 读取properties文件的配置信息
     */
    Properties properties = new Properties();

    /**
     * 构造会话工厂，默认读取配置文件并且生成配置
     */
    public DefaultSqlSessionFactory() {
        logger.info("开始加载配置文件并构建配置信息");
        // 加载配置文件
        try(InputStream inputStream = new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()+"database.properties")) {
            // 读取配置信息
            properties.load(inputStream);
            // 开始构建配置信息
            configuration.setConfiguration(new Configuration.Builder()
                    .addUser(properties.getProperty("user"))
                    .addUrl(properties.getProperty("url"))
                    .addPassword(properties.getProperty("password"))
                    .addDriverClass(properties.getProperty("driverClass"))
                    .build());
        } catch (FileNotFoundException e) {
            logger.error("database文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatabaseConfigException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public SqlSession openSqlSession() {
        DataSource dataSource = new DefaultDataSource(configuration);
        return new DefaultSqlSession(dataSource);
    }
}
