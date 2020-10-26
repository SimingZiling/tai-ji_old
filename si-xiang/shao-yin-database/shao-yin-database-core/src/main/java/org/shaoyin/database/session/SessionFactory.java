package org.shaoyin.database.session;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Session工厂接口
 */
public interface SessionFactory {

    /**
     * 开启会话
     * @return Session
     */
    Session openSession();

    /**
     * 关闭session工厂
     */
    void close() throws SQLException, DatabaseCoreException;

}
