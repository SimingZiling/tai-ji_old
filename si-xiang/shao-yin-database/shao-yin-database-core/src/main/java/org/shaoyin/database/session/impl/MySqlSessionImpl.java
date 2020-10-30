package org.shaoyin.database.session.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.pool.ConnectionPool;
import org.shaoyin.database.orm.ObjectRelationMapping;
import org.shaoyin.database.session.Session;
import org.shaoyin.database.sql.PackagSQL;
import org.shaoyin.database.util.ColumnInfo;
import org.yang.localtools.exception.LocalToolsException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Session实现类
 */
public class MySqlSessionImpl implements Session {

    protected Connection connection;

    protected PreparedStatement preparedStatement = null;

    protected ResultSet resultSet = null;


    @Override
    public void close() throws SQLException {
        // 验证Connection是否为空。如果不为空则调用链接池释放链接方法
        if(connection != null){
            if(preparedStatement != null){
                if(resultSet != null){
                    this.closeResultSet();
                }
                this.closePreparedStatement();
            }
            this.closeConnection();
        }
    }

    @Override
    public void beginTransaction() throws SQLException {
        // 当链接不为空并且为自动提交时，设置事务提交方式为手动提交
        if(connection != null && connection.getAutoCommit()){
            connection.setAutoCommit(false);
        }
    }

    @Override
    public void commitTransaction() throws SQLException {
        // 当链接不为空，并且事务提交为手动时，进行事务提交
        if(connection != null && !connection.getAutoCommit()){
            connection.commit();
        }
    }

    @Override
    public void rollBackTransaction() throws SQLException {
        // 当链接不为空，并且事务提交为手动时，进行事务回滚
        if(connection != null && !connection.getAutoCommit()){
            connection.rollback();
        }
    }

    @Override
    public int execSQL(String sql) throws SQLException {
        return execSQL(sql,null);
    }

    @Override
    public int execSQL(String sql, List<Object> paramList) throws SQLException {
        return getPreparedStatement(sql,paramList).executeUpdate();
    }

    @Override
    public Object insert(String sql, List<Object> paramList, boolean isReturnPrimaryKey) throws SQLException, DatabaseCoreException {

        int execResult = execSQL(sql,paramList);
        if(execResult != 0){
            if(isReturnPrimaryKey){
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getObject(1);
            }else return execResult;
        }else {
            throw new DatabaseCoreException("添加失败！");
        }
    }

    @Override
    public int insert(String sql, List<Object> paramList) throws SQLException, DatabaseCoreException {
        return Integer.parseInt(String.valueOf(insert(sql,paramList,false)));
    }

    @Override
    public int insert(String sql) throws SQLException, DatabaseCoreException {
        return insert(sql,null);
    }

    @Override
    public <T> T insert(T t) throws LocalToolsException, DatabaseCoreException, SQLException {
        try {
            Field field = t.getClass().getDeclaredField(ColumnInfo.getKeyName(t.getClass(),true));
            // 开启私有属性访问权限
            field.setAccessible(true);
            // 提交sql并设置返回结果
            if(field.getType().equals(Integer.class) || field.getType().equals(int.class)){
                field.set(t,Integer.valueOf(String.valueOf(insert(PackagSQL.insert(t),null,true))));
            }else {
                try {
                    throw new DatabaseCoreException("未知属性："+field.getType());
                }catch (DatabaseCoreException e){
                    e.printStackTrace();
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T> List<T> insert(List<T> tList) throws DatabaseCoreException, SQLException, LocalToolsException {
        List<T> listT = new ArrayList<>();
        for (T t : tList){
            listT.add(insert(t));
        }
        return listT;
    }

    @Override
    public int delete(String sql, List<Object> paramList) throws SQLException {
        return execSQL(sql,paramList);
    }

    @Override
    public int delete(String sql) throws SQLException {
        return execSQL(sql);
    }

    @Override
    public <T> int delete(T t) throws SQLException {
        return execSQL(PackagSQL.delete(t));
    }

    @Override
    public int update(String sql, List<Object> paramList) throws SQLException {
        return execSQL(sql,paramList);
    }

    @Override
    public int update(String sql) throws SQLException {
        return execSQL(sql);
    }


//
//    @Override
//    public int delete(String sql, List<Object> paramList) throws SQLException {
//        // 删除和更新采用同一个接口
//        return update(sql,paramList);
//    }
//
//    @Override
//    public <T> int delete(T t) {
//        return 0;
//    }
//
//    @Override
//    public <T> int delete(List<T> tList) {
//        return 0;
//    }
//
//    @Override
//    public <T> int delete(Class<T> clazz, Map<String, Object> paramMap) {
//        return 0;
//    }
//
//    @Override
//    public <T> int delete(Class<T> clazz, List<Map<String, Object>> paramMapList) {
//        return 0;
//    }
//
//    @Override
//    public int update(String sql, List<Object> paramList) throws SQLException {
//        return getPreparedStatement(sql, paramList).executeUpdate();
//    }
//
//    @Override
//    public <T> int update(T t) {
//        return 0;
//    }
//
//    @Override
//    public <T> int update(List<T> tList) {
//        return 0;
//    }
//
//    @Override
//    public <T> int update(Class<T> clazz, Map<String, Object> paramMap) {
//        return 0;
//    }
//
//    @Override
//    public <T> int update(Class<T> clazz, List<Map<String, Object>> paramMapList) {
//        return 0;
//    }
//
//    @Override
//    public Map<String, Object> select(String sql, List<Object> paramList) throws SQLException {
//        // 将结果集转换为Map
//        return ObjectRelationMapping.handleResultSetToMap(getQueryResultSet(sql, paramList));
//    }
//
//    @Override
//    public List<Map<String, Object>> selectList(String sql, List<Object> paramList) throws SQLException {
//        // 将结果集转换为Map列表
//        return ObjectRelationMapping.handleResultSetToMapList(getQueryResultSet(sql, paramList));
//    }
//
//    @Override
//    public <T> T select(String sql, List<Object> paramList, Class<T> clazz) {
//        return null;
//    }
//
//    @Override
//    public <T> List<T> selectList(String sql, List<Object> paramList, Class<T> clazz) {
//        return null;
//    }
//
    /**
     * 获取声明
     * @param sql sql语句
     * @param paramList 参数列表
     * @return 参数
     */
    private PreparedStatement getPreparedStatement(String sql, List<Object> paramList) throws SQLException {
        // TODO 打印sql
        System.out.println(sql);
        // 获取声明，设置返回主键
        preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        // 如果参数不为空 则封装参数
        if(paramList != null && !paramList.isEmpty()) {
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setObject(i + 1, paramList.get(i));
            }
        }
        return preparedStatement;
    }

    /**
     * 获取查询结果集
     * @param sql SQL语句
     * @param paramList 参数列表
     * @return 结果集
     */
    private ResultSet getQueryResultSet(String sql, List<Object> paramList) throws SQLException {
        return getPreparedStatement(sql,paramList).executeQuery();
    }

    /**
     * 关闭结果集
     */
    private void closeResultSet() throws SQLException {
        if(resultSet != null){
            resultSet.close();
        }
    }

    /**
     * 关闭申明
     */
    private void closePreparedStatement() throws SQLException {
        if(preparedStatement != null){
            preparedStatement.close();
        }
    }

    /**
     * 关闭连接
     */
    private void closeConnection() throws SQLException {
        if(connection != null){
            // 释放链接
            ConnectionPool.releaseConnection(connection);
        }
    }

}
