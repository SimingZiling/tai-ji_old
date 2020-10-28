package org.shaoyin.database.jdbc.util;

import org.shaoyin.database.jdbc.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC扩展功能
 */
public class JDBCUtil {

    private JDBCUtil(){}

    static {
        try {
            Class.forName(DatabaseConfiguration.getDriverClass());
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DatabaseConfiguration.getUrl(),
                    DatabaseConfiguration.getUser(),
                    DatabaseConfiguration.getPassword());
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
