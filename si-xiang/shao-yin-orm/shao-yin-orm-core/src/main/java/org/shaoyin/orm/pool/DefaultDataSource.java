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
 * 默认数据源，虽然在池子里，但是并没有实现连接池
 */
public class DefaultDataSource implements DataSource {

    /** 配置信息 */
    private final Configuration configuration;

    /**
     * 默认数据源构造方法，加载数据库驱动
     * @param configuration 数据源配置
     */
    public DefaultDataSource(Configuration configuration) {
        this.configuration = configuration;
        try {
            Class.forName(configuration.getDriverClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // 获取连接
        return DriverManager.getConnection(configuration.getUrl(),configuration.getUser(),configuration.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(configuration.getUrl(),username,password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("未实现getLogWriter方法！");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException("未实现setLogWriter方法！");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException("未实现setLoginTimeout方法！");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("未实现getLoginTimeout方法！");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("未实现getParentLogger方法！");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("未实现unwrap方法！");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("未实现isWrapperFor方法！");
    }
}
