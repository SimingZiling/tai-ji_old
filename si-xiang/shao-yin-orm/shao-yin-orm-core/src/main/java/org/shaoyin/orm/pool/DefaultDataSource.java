package org.shaoyin.orm.pool;

import org.shaoyin.orm.config.Configuration;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 默认数据源
 */
public class DefaultDataSource implements DataSource {

    private final Configuration configuration;

    /**
     * 构造方法获设置配置
     * @param configuration 配置信息
     */
    public DefaultDataSource(Configuration configuration){
        this.configuration = configuration;
        try {
            Class.forName(configuration.getDriverClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // 从数据库直接获取
        return DriverManager.getConnection(configuration.getUrl(),
                configuration.getUser(),
                configuration.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(configuration.getUrl(),username,password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("数据源不支持getParentLogger方法！");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("不支持unwrap方法！");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("不支持isWrapperFor方法！");
    }
}
