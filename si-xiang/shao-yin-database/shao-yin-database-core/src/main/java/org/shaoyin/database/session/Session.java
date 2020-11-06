package org.shaoyin.database.session;


import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.exception.DoNotCreateException;
import org.yang.localtools.exception.LocalToolsException;

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
     void close() throws  SQLException;

     /**
      * 开启事务
      */
     void beginTransaction() throws  SQLException;

     /**
      * 提交事务
      */
     void commitTransaction() throws SQLException;

     /**
      * 事务回滚
      */
     void rollBackTransaction() throws SQLException;

     /**
      * 执行一条sql
      * @param sql sql
      * @return 执行结果
      */
     int execUpdateSQL(String sql) throws SQLException;

     /**
      * 执行sql，并且携带参数
      * 参数列表顺序与语句中问号对应
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 执行结果
      */
     int execUpdateSQL(String sql,List<Object> paramList) throws SQLException;

     /**
      * 创建表
      * @param clazz 类
      */
     void createTable(Class<?> clazz) throws DoNotCreateException, SQLException;

     void deleteTable(Class<?> clazz) throws DoNotCreateException, SQLException;

     /**
      * 插入数据
      * 如果不返回主键则返回执行是否成功
      * @param sql 插入的sql语句
      * @param paramList 参数
      * @param isReturnPrimaryKey 是否返回主键
      * @return 返回主键或执行结果
      */
     Object insert(String sql,List<Object> paramList,boolean isReturnPrimaryKey) throws SQLException, DatabaseCoreException;

     /**
      * 插入数据
      * @param sql 插入的sql语句
      * @param paramList 参数
      * @return 返回执行结果
      */
     int insert(String sql,List<Object> paramList) throws SQLException, DatabaseCoreException;

     /**
      * 插入数据
      * @param sql 插入的sql语句
      * @return 返回执行结果
      */
     int insert(String sql) throws SQLException, DatabaseCoreException;

     /**
      * 插入数据
      * @param t 对象
      * @return 对象
      */
     <T> T insert(T t) throws LocalToolsException, DatabaseCoreException, SQLException;

     /**
      * 插入数据列表
      * @param tList 对象列表
      * @return 对象列表
      */
     <T> List<T> insert(List<T> tList) throws DatabaseCoreException, SQLException, LocalToolsException;


     /**
      * 删除数据
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 执行结果
      */
     int delete(String sql,List<Object> paramList) throws SQLException;

     /**
      * 删除数据
      * @param sql 数据sql
      * @return 执行结果
      */
     int delete(String sql) throws SQLException;

     /**
      * 删除数据
      * @param t 对象
      * @return 执行结果
      */
     <T> int delete(T t) throws SQLException;

     <T> void delete(List<T> tList) throws SQLException;

     /**
      * 更新数据
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 执行结果
      */
     int update(String sql,List<Object> paramList) throws SQLException;

     /**
      * 更新数据
      * @param sql sql语句
      * @return 执行结果
      */
     int update(String sql) throws SQLException;

     /**
      * 查询数据
      * @param sql sql语句
      * @param paramList 参数
      * @return 查询结果Map
      */
     Map<String,Object> select(String sql,List<Object> paramList) throws SQLException;

     /**
      * 查询数据
      * @param sql sql语句
      * @return 查询结果Map
      */
     Map<String,Object> select(String sql) throws SQLException;

     /**
      * 查询数据列表
      * @param sql sql语句
      * @param paramList 参数
      * @return 查询结果Map列表
      */
     List<Map<String,Object>> selectList(String sql,List<Object> paramList) throws SQLException;

     /**
      * 查询数据列表
      * @param sql sql语句
      * @return 查询结果Map列表
      */
     List<Map<String,Object>> selectList(String sql) throws SQLException;

     /**
      * 查询数据
      * @param sql sql语句
      * @param paramList 参数
      * @param clazz 类
      * @return 对象
      */
     <T> T select(String sql,List<Object> paramList,Class<T> clazz) throws SQLException;

     /**
      * 查询数据
      * @param sql sql语句
      * @param clazz 类
      * @return 对象
      */
     <T> T select(String sql,Class<T> clazz) throws SQLException;

     /**
      * 查询数据列表
      * @param sql sql语句
      * @param paramList 参数
      * @param clazz 类
      * @return 对象
      */
     <T> List<T> selectList(String sql,List<Object> paramList,Class<T> clazz) throws SQLException;

     /**
      * 查询数据列表
      * @param sql sql语句
      * @param clazz 类
      * @return 对象
      */
     <T> List<T> selectList(String sql,Class<T> clazz) throws SQLException;

}
