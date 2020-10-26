package org.shaoyin.database.session.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.pool.ConnectionPool;
import org.shaoyin.database.session.Session;
import org.shaoyin.database.session.SessionFactory;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Session工厂接口实现
 */
public class SessionFactoryImpl implements SessionFactory {

    private Set<SessionImpl> sessions = new HashSet<>();

    @Override
    public Session openSession() {
        SessionImpl session = new SessionImpl();
        // 从连接池获取一个数据库连接 并且提供Session使用
        session.connection = ConnectionPool.getConnection();
        sessions.add(session);
        return session;
    }

    @Override
    public void close() throws DatabaseCoreException {
        // 判断Session列表是否为空
        if(!sessions.isEmpty()){
            // 遍历Session
            for (Session session : sessions){
                // 关闭Session
                session.close();
            }
        }
    }
}
