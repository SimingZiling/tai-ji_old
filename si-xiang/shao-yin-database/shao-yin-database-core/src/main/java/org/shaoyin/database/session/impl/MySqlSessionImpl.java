package org.shaoyin.database.session.impl;

import org.shaoyin.database.jdbc.pool.ConnectionPool;
import org.shaoyin.database.orm.ObjectRelationMapping;
import org.shaoyin.database.session.Session;

import java.sql.*;
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
    public int insert(String sql, List<Object> paramList,boolean generatedKeys) throws SQLException {
        // 返回生成的主键 如果不需要返回则返回结果
        if(generatedKeys){
            resultSet = getPreparedStatement(sql,paramList).getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        }else {
            return getPreparedStatement(sql,paramList).executeUpdate();
        }
    }

    @Override
    public <T> T insert(T t) {

        return null;
    }

    @Override
    public <T> List<T> insert(List<T> tList) {
        return null;
    }

    @Override
    public <T> T insert(Class<T> clazz, Map<String, Object> param) {
        return null;
    }

    @Override
    public <T> List<T> insert(Class<T> clazz, List<Map<String, Object>> paramList) {
        return null;
    }

    @Override
    public int delete(String sql, List<Object> paramList) throws SQLException {
        // 删除和更新采用同一个接口
        return update(sql,paramList);
    }

    @Override
    public <T> int delete(T t) {
        return 0;
    }

    @Override
    public <T> int delete(List<T> tList) {
        return 0;
    }

    @Override
    public <T> int delete(Class<T> clazz, Map<String, Object> paramMap) {
        return 0;
    }

    @Override
    public <T> int delete(Class<T> clazz, List<Map<String, Object>> paramMapList) {
        return 0;
    }

    @Override
    public int update(String sql, List<Object> paramList) throws SQLException {
        return getPreparedStatement(sql,paramList).executeUpdate();
    }

    @Override
    public <T> int update(T t) {
        return 0;
    }

    @Override
    public <T> int update(List<T> tList) {
        return 0;
    }

    @Override
    public <T> int update(Class<T> clazz, Map<String, Object> paramMap) {
        return 0;
    }

    @Override
    public <T> int update(Class<T> clazz, List<Map<String, Object>> paramMapList) {
        return 0;
    }

    @Override
    public Map<String, Object> select(String sql, List<Object> paramList) throws SQLException {
        // 将结果集转换为Map
        return ObjectRelationMapping.handleResultSetToMap(getQueryResultSet(sql, paramList));
    }

    @Override
    public List<Map<String, Object>> selectList(String sql, List<Object> paramList) throws SQLException {
        // 将结果集转换为Map列表
        return ObjectRelationMapping.handleResultSetToMapList(getQueryResultSet(sql, paramList));
    }

    @Override
    public <T> T select(String sql, List<Object> paramList, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> List<T> selectList(String sql, List<Object> paramList, Class<T> clazz) {
        return null;
    }

    /**
     * 获取声明
     * @param sql sql语句
     * @param paramList 参数列表
     * @return 参数
     */
    private PreparedStatement getPreparedStatement(String sql, List<Object> paramList) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < paramList.size(); i++) {
            preparedStatement.setObject(i + 1, paramList.get(i));
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
