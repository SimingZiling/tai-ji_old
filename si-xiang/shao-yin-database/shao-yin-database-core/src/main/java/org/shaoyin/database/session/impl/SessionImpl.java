package org.shaoyin.database.session.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.session.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Session实现类
 */
public class SessionImpl implements Session {

    protected Connection connection;

    @Override
    public void close() throws DatabaseCoreException {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseCoreException(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public int insert(String sql, Object... object) {
        System.out.println(object.length);
        for (Object o : object){
            System.out.println(String.valueOf(o));
        }
        return 0;
    }

    @Override
    public <T> T insert(T t) {
        return null;
    }

    public static void main(String[] args) {
        SessionImpl session = new SessionImpl();
        List<Object> objects = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();
        objectList.add("22");
        objectList.add("44");
        objects.add(objectList);
        session.insert("22",objects);
    }

}
