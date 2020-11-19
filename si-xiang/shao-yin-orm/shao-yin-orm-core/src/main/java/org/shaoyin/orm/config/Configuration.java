package org.shaoyin.orm.config;

import org.shaoyin.orm.exception.DatabaseConfigException;
import org.yang.localtools.verify.StringVerify;

/**
 * 数据库配置信息
 */
public class Configuration {

    public Configuration() {}

    public Configuration(Configuration configuration) {
        this.user = configuration.user;
        this.url = configuration.url;
        this.driverClass = configuration.driverClass;
        this.password = configuration.password;
    }

    public Configuration setConfiguration(Configuration configuration){
        this.user = configuration.user;
        this.url = configuration.url;
        this.driverClass = configuration.driverClass;
        this.password = configuration.password;
        return this;
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
     * 内部构造者类（使用构造者模式进行数据库构造）
     */
    public static class Builder{

        private final Configuration configuration;

        public Builder() {
            configuration = new Configuration();
        }

        public Builder addUser(String user){
            configuration.user = user;
            return this;
        }

        public Builder addUrl(String url){
            configuration.url = url;
            return this;
        }

        public Builder addPassword(String password){
            configuration.password = password;
            return this;
        }

        public Builder addDriverClass(String driverClass){
            configuration.driverClass = driverClass;
            return this;
        }

        /**
         * 手动构建配置信息
         * @return 配置信息
         */
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
