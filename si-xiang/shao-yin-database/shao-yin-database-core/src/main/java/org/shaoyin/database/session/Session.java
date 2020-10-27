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
      * @param paramList 参数列表
      * @return 主键id 或者添加结果
      */
     int insert(String sql,List<Object> paramList,boolean GeneratedKeys) throws SQLException, DatabaseCoreException;

     /**
      * 插入数据
      * @param t 泛型对象
      * @return 对象
      */
     <T> T insert(T t);

     /**
      * 批量插入数据
      * @param tList 对象列表
      * @return 对象列表
      */
     <T> List<T> insert(List<T> tList);

     /**
      * 插入数据
      * @param clazz 对象
      * @param param 参数
      * @return 对象
      */
     <T> T insert(Class<T> clazz, Map<String,Object> param);

     /**
      * 批量插入数据
      * @param clazz 类
      * @param paramList 参数列表
      * @return 对象列表
      */
     <T> List<T> insert(Class<T> clazz,List<Map<String,Object>> paramList);




     /*删除*/

     /**
      * 删除数据
      * @param sql sql语句
      * @param paramList 删除对象
      * @return 删除结果
      */
     int delete(String sql,List<Object> paramList) throws DatabaseCoreException;

     /**
      * 删除数据
      * @param t 对象
      * @return 删除结果
      */
     <T> int delete(T t);

     /**
      * 删批量删除数据
      * @param tList 数据列表
      * @return 删除结果
      */
     <T> int delete(List<T> tList);


     /**
      * 删除数据
      * @param clazz 类
      * @param paramMap 参数Map
      * @return 删除结果
      */
     <T> int delete(Class<T> clazz,Map<String,Object> paramMap);

     /**
      * 批量删除数据
      * @param paramMapList 参数Map列表
      * @return 删除结果
      */
     <T> int delete(Class<T> clazz,List<Map<String,Object>> paramMapList);

     /*更新*/

     /**
      * 更新数据
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 更新结果
      */
     int update(String sql,List<Object> paramList) throws DatabaseCoreException;

     /**
      * 更新数据
      * @param t 对象
      * @return 更新参数
      */
     <T> int update(T t);

     /**
      * 更新数据列表
      * @param tList 对象列表
      * @return 更新结果
      */
     <T> int update(List<T> tList);

     /**
      * 更新数据
      * @param clazz 类
      * @param paramMap 参数Map
      * @return 更新结果
      */
     <T> int update(Class<T> clazz,Map<String,Object> paramMap);

     /**
      * 批量更新数据
      * @param paramMapList 参数Map列表
      * @return 更新结果
      */
     <T> int update(Class<T> clazz,List<Map<String,Object>> paramMapList);

     /*查询*/

     /**
      * 查询数据
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 数据Map
      */
     Map<String,Object> select(String sql,List<Object> paramList) throws DatabaseCoreException;


     /**
      * 查询数据列表
      * @param sql sql语句
      * @param paramList 参数列表
      * @return 数据Map
      */
     List<Map<String,Object>> selectList(String sql,List<Object> paramList) throws DatabaseCoreException;

     /**
      * 查询数据
      * @param sql sql语句
      * @param paramList 参数列表
      * @param clazz 类
      * @return 对象
      */
     <T> T select(String sql,List<Object> paramList,Class<T> clazz);

     /**
      * 查询数据列表
      * @param sql sql语句
      * @param paramList 参数列表
      * @param clazz 类
      * @return 对象列表
      */
     <T> List<T> selectList(String sql,List<Object> paramList,Class<T> clazz);
}
