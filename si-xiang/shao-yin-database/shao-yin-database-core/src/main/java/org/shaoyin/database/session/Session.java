package org.shaoyin.database.session;

import java.sql.Connection;

/**
 * Session接口
 */
public interface Session {


     void save();
     void save(Object object);
     void update();
     void delete();
     void find(Class<?> clazz);
     void findList(Class<?> clazz);
}
