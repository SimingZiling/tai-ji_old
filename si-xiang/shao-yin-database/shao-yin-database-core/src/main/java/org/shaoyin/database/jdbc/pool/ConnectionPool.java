package org.shaoyin.database.jdbc.pool;

import org.shaoyin.database.jdbc.pool.config.ConnectionPoolConfiguration;
import org.shaoyin.database.jdbc.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 连接池
 */
public class ConnectionPool {

    /**
     * 连接池列表
     */
    private static Set<Connection> connectionSet = new HashSet<>();

    /**
     * 获取连接数量
     * @return 连接数量
     */
    public static int getConnectionNumber(){
        return connectionSet.size();
    }


    /**
     * 施放连接
     * @param connection 连接
     */
    public static void releaseConnection(Connection connection) throws SQLException {
        if(connection != null) {
            // 当连接数达到上限时时 将连接放回连接池
            if (getConnectionNumber() < ConnectionPoolConfiguration.getConnectionMaxNumber()) {
                connectionSet.add(connection);
            } else {
                connection.close();
            }
        }
    }

    /**
     * 加载连接池
     */
    public static void loadConnectionPool(){
        for (int i = 0; i < ConnectionPoolConfiguration.getConnectionMinNumber(); i++){
            Connection connection = JDBCUtil.getConnection();
            if (connection != null) {
                connectionSet.add(connection);
            }
        }
    }

    /**
     * 获取连接
     * @return 连接
     */
    public static Connection getConnection(){
        // 如果连接池中有连接则使用连接池中的连接，如果没有则创建一个连接
        if (connectionSet.size() != 0){
            Connection connection = connectionSet.iterator().next();
            connectionSet.remove(connection);
            return connection;
        }else {
            return JDBCUtil.getConnection();
        }
    }
}
