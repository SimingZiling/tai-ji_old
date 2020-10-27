package org.shaoyin.database.session.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.pool.ConnectionPool;
import org.shaoyin.database.session.Session;
import org.shaoyin.database.session.SessionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Session工厂接口实现
 */
public class SessionFactoryImpl implements SessionFactory {

    private final Set<MySqlSessionImpl> sessions = new HashSet<>();

    @Override
    public Session openSession() {
        MySqlSessionImpl session = new MySqlSessionImpl();
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
