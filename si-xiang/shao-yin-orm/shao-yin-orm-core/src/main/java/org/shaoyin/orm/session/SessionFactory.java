package org.shaoyin.orm.session;

import org.shaoyin.orm.exception.DatabaseCoreException;

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
    void close() throws DatabaseCoreException, SQLException;

}
