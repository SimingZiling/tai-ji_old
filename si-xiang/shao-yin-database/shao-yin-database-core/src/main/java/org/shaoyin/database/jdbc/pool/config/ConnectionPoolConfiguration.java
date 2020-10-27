package org.shaoyin.database.jdbc.pool.config;


/**
 * 连接池配置
 */
public class ConnectionPoolConfiguration {

    /**
     * 连接最大数 默认值为：10
     */
    private static int connectionMaxNumber = 10;

    /**
     * 连接最小数 默认值为：5
     */
    private static int connectionMinNumber = 5;

    public static int getConnectionMaxNumber() {
        return connectionMaxNumber;
    }


    public static int getConnectionMinNumber() {
        return connectionMinNumber;
    }

}