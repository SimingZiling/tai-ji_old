package org.shaoyin.database.config;

import org.shaoyin.database.jdbc.config.DatabaseConfiguration;
import org.shaoyin.database.jdbc.pool.config.ConnectionPoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yang.localtools.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 配置类
 */
public class Configuration {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Configuration(){
        logger.info("开始加载数据库配置文件");
        try(InputStream inputStream = new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()+"database.properties")) {
            //创建Properties对象用于读取**.properties文件
            Properties properties = new Properties();
            properties.load(inputStream);
            configurationMap.putAll(properties);
        } catch (FileNotFoundException e) {
            logger.error("database文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Map<Object, Object> configurationMap = new HashMap<>();

    /**
     * 加载数据库配置
     */
    public void doLoadDatabaseConfiguration(){
        DatabaseConfiguration.setDriverClass(String.valueOf(configurationMap.get("driverClass")));
        DatabaseConfiguration.setPassword(String.valueOf(configurationMap.get("password")));
        DatabaseConfiguration.setUrl(String.valueOf(configurationMap.get("url")));
        DatabaseConfiguration.setUser(String.valueOf(configurationMap.get("user")));
    }

    /**
     * 加载连接池配置
     */
    public void  doLoadConnectionPoolConfiguration(){
        Object connectionMaxNumber = configurationMap.get("maxConnection");
        if(connectionMaxNumber != null) {
            ConnectionPoolConfiguration.setConnectionMaxNumber(Integer.parseInt(String.valueOf(connectionMaxNumber)));
        }
        Object connectionMinNumber = configurationMap.get("minConnection");
        if(connectionMinNumber != null){
            ConnectionPoolConfiguration.setConnectionMinNumber(Integer.parseInt(String.valueOf(connectionMinNumber)));
        }
    }

}
