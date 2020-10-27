package org.shaoyin.database.session.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
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
    public void close() throws DatabaseCoreException {
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

    /**
     * 开始事务
     */
    public void beginTransaction() throws DatabaseCoreException {
        try {
            // 当链接不为空并且为自动提交时，设置事务提交方式为手动提交
            if(connection != null && connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
    }

    /**
     * 提交事务
     */
    public void commitTransaction() throws DatabaseCoreException {
        try {
            // 当链接不为空，并且事务提交为手动时，进行事务提交
            if(connection != null && !connection.getAutoCommit()){
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
    }

    /**
     * 回滚事务
     */
    public void rollBackTransaction() throws DatabaseCoreException {
        try {
            // 当链接不为空，并且事务提交为手动时，进行事务回滚
            if(connection != null && !connection.getAutoCommit()){
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
    }


    @Override
    public int insert(String sql, List<Object> paramList,boolean GeneratedKeys) throws DatabaseCoreException {
        // 返回生成的主键
        try {
            if(GeneratedKeys){
                preparedStatement = getPreparedStatement(sql,paramList);
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            }else {
                return getPreparedStatement(sql,paramList).executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
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
    public int delete(String sql, List<Object> paramList) throws DatabaseCoreException {
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
    public int update(String sql, List<Object> paramList) throws DatabaseCoreException {
        try {
            return getPreparedStatement(sql,paramList).executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
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
    public Map<String, Object> select(String sql, List<Object> paramList) throws DatabaseCoreException {
        // 将结果集转换为Map
        return ObjectRelationMapping.handleResultSetToMap(getQueryResultSet(sql, paramList));
    }

    @Override
    public List<Map<String, Object>> selectList(String sql, List<Object> paramList) throws DatabaseCoreException {
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
    private PreparedStatement getPreparedStatement(String sql, List<Object> paramList) throws DatabaseCoreException {
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setObject(i + 1, paramList.get(i));
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
    }

    /**
     * 获取查询结果集
     * @param sql SQL语句
     * @param paramList 参数列表
     * @return 结果集
     */
    private ResultSet getQueryResultSet(String sql, List<Object> paramList) throws DatabaseCoreException {
        try {
            return getPreparedStatement(sql,paramList).executeQuery();
        } catch (SQLException e) {
            throw new DatabaseCoreException(e.getLocalizedMessage());
        }
    }

    /**
     * 关闭结果集
     */
    private void closeResultSet() throws DatabaseCoreException {
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DatabaseCoreException(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 关闭申明
     */
    private void closePreparedStatement() throws DatabaseCoreException {
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DatabaseCoreException(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 关闭连接
     */
    private void closeConnection() throws DatabaseCoreException {
        if(connection != null){
            try {
                ConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                throw new DatabaseCoreException(e.getLocalizedMessage());
            }
        }
    }

}
