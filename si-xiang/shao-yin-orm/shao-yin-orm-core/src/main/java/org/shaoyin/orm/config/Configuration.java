package org.shaoyin.orm.config;

import org.shaoyin.orm.exception.DatabaseConfigException;
import org.shaoyin.orm.jdbc.config.DatabaseConfiguration;
import org.shaoyin.orm.jdbc.pool.config.ConnectionPoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yang.localtools.util.StringUtil;
import org.yang.localtools.verify.StringVerify;

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
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public Configuration(){
//        logger.info("开始加载数据库配置文件");
//        try(InputStream inputStream = new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath()+"database.properties")) {
//            //创建Properties对象用于读取**.properties文件
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            configurationMap.putAll(properties);
//        } catch (FileNotFoundException e) {
//            logger.error("database文件不存在");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private final Map<Object, Object> configurationMap = new HashMap<>();
//
//    /**
//     * 加载数据库配置
//     */
//    public void doLoadDatabaseConfiguration(){
//        DatabaseConfiguration.setDriverClass(String.valueOf(configurationMap.get("driverClass")));
//        DatabaseConfiguration.setPassword(String.valueOf(configurationMap.get("password")));
//        DatabaseConfiguration.setUrl(String.valueOf(configurationMap.get("url")));
//        DatabaseConfiguration.setUser(String.valueOf(configurationMap.get("user")));
//    }
//
//    /**
//     * 加载连接池配置
//     */
//    public void  doLoadConnectionPoolConfiguration(){
//        Object connectionMaxNumber = configurationMap.get("maxConnection");
//        if(connectionMaxNumber != null) {
//            ConnectionPoolConfiguration.setConnectionMaxNumber(Integer.parseInt(String.valueOf(connectionMaxNumber)));
//        }
//        Object connectionMinNumber = configurationMap.get("minConnection");
//        if(connectionMinNumber != null){
//            ConnectionPoolConfiguration.setConnectionMinNumber(Integer.parseInt(String.valueOf(connectionMinNumber)));
//        }
//    }


    public Configuration() {}

    public Configuration(Configuration configuration) {
        this.user = configuration.user;
        this.url = configuration.url;
        this.driverClass = configuration.driverClass;
        this.password = configuration.password;
    }

    public void setConfiguration(Configuration configuration){
        this.user = configuration.user;
        this.url = configuration.url;
        this.driverClass = configuration.driverClass;
        this.password = configuration.password;
    }

    /**数据库地址*/
    private String url;
    /**数据库链接密码*/
    private String password;
    /**数据库驱动*/
    private String driverClass;
    /**数据库用户名*/
    private String user;

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUser() {
        return user;
    }

    /**
     * 构建
     */
    public static class Builder{

        private Configuration configuration;

        public Builder() {
            this.configuration = new Configuration();
        }

        public Builder addUser(String user){
            this.configuration.user = user;
            return this;
        }

        public Builder addUrl(String url){
            this.configuration.url = url;
            return this;
        }

        public Builder addPassword(String password){
            this.configuration.password = password;
            return this;
        }

        public Builder addDriverClass(String driverClass){
            this.configuration.driverClass = driverClass;
            return this;
        }

        public Configuration build() throws DatabaseConfigException {
            verifyConfiguration(configuration.user,"数据库用户名不存在！");
            verifyConfiguration(configuration.driverClass,"数据库驱动不存在！");
            verifyConfiguration(configuration.password,"数据库密码不存在！");
            verifyConfiguration(configuration.url,"数据库链接地址不存在！");
            return new Configuration(configuration);
        }

        /**
         * 验证配置
         * @param config 配置
         * @param errorMsg 报错信息
         */
        private void verifyConfiguration(String config,String errorMsg) throws DatabaseConfigException {
            if(StringVerify.isBlank(config)){
                throw new DatabaseConfigException(errorMsg);
            }
        }
    }
}
