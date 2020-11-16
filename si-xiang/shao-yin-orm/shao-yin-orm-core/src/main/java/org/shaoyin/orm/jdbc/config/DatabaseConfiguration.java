package org.shaoyin.orm.jdbc.config;


/**
 * 数据库配置
 */
public class DatabaseConfiguration {

    private DatabaseConfiguration(){}

    /**
     * 驱动类
     */
    private static String driverClass;
    /**
     * 连接地址
     */
    private static String url;
    /**
     * 数据库用户名
     */
    private static String user;
    /**
     * 数据库密码
     */
    private static String password;

    public static String getDriverClass() {
        return driverClass;
    }

    public static void setDriverClass(String driverClass) {
        DatabaseConfiguration.driverClass = driverClass;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DatabaseConfiguration.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DatabaseConfiguration.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatabaseConfiguration.password = password;
    }
}
