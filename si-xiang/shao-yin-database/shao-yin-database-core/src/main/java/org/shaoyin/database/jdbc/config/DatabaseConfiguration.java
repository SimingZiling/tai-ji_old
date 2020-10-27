package org.shaoyin.database.config;


/**
 * 数据库配置
 */
public class DatabaseConfiguration {

    /**
     * 驱动类
     */
    private static String DriverClass;
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
        return DriverClass;
    }

    public static void setDriverClass(String driverClass) {
        DriverClass = driverClass;
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
