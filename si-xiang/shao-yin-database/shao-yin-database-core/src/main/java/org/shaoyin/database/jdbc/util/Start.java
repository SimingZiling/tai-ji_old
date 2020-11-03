package org.shaoyin.database.jdbc.util;

import org.shaoyin.database.jdbc.config.DatabaseConfiguration;
import org.shaoyin.database.jdbc.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class Start {

    public static void main(String[] args) throws SQLException {

        // 构建数据库配置
        DatabaseConfiguration.setDriverClass("com.mysql.cj.jdbc.Driver");
        DatabaseConfiguration.setUrl("jdbc:mysql://132.232.19.205:3306/demo?autoReconnect=true&useSSL=false&serverTimezone=GMT%2b8&characterEncoding=utf8&allowMultiQueries=true");
        DatabaseConfiguration.setPassword("angel83528358");
        DatabaseConfiguration.setUser("root");

        ConnectionPool.loadConnectionPool();
        System.out.println(ConnectionPool.getConnectionNumber());
        Connection c = null;
        for (int i = 0;i<=6;i++){
            c = ConnectionPool.getConnection();

        }
        ConnectionPool.releaseConnection(c);
        System.out.println(ConnectionPool.getConnectionNumber());
//        ConnectionPool.releaseConnection(c);
        System.out.println(ConnectionPool.getConnectionNumber());
    }


}
