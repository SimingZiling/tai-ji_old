package org.shaoyin.database.session;

import org.shaoyin.database.exception.DatabaseCoreException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Session接口
 */
public interface Session {


     /**
      * 关闭Session
      */
     void close() throws DatabaseCoreException;

     /*添加*/

     /**
      * 插入数据
      * @param sql sql 语句
      * @param object 多参数（可以是一个，也可以是多个）
      * @return 主键id 或者添加结果
      */
     int insert(String sql,Object ...object);


     /**
      * 插入数据
      * @param t 泛型对象
      * @return 对象
      */
     <T> T insert(T t);


}
