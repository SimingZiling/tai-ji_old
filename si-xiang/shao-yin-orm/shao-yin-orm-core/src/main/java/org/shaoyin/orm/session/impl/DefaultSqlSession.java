package org.shaoyin.orm.session.impl;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.shaoyin.orm.exception.DatabaseCoreException;
import org.shaoyin.orm.exception.DoNotCreateException;
import org.shaoyin.orm.orm.ObjectRelationMapping;
import org.shaoyin.orm.session.SqlSession;
import org.shaoyin.orm.sql.PackagSQL;
import org.shaoyin.orm.util.ColumnInfo;
import org.yang.localtools.exception.LocalToolsException;
import org.yang.localtools.util.ClassUtil;
import org.yang.localtools.util.MapUtil;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 默认的Sql会话
 */
public class DefaultSqlSession implements SqlSession {

    private Connection connection;

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    public DefaultSqlSession(DataSource dataSource) {
        try {
            // 获取连接
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行查询SQL
     * @param sql SQL语句
     * @param paramList 参数列表
     * @return 结果集
     */
    private ResultSet execQuerySQL(String sql, List<Object> paramList,boolean isASingle) throws SQLException {
        return getPreparedStatement(sql,paramList,true,isASingle).executeQuery();
    }

    /**
     * 执行查询SQL
     * @param sql SQL语句
     * @return 结果集
     */
    private ResultSet execQuerySQL(String sql,boolean isASingle) throws SQLException {
        return execQuerySQL(sql,null, isASingle);
    }


    @Override
    public void close() {
        try {
            if(connection != null){
                if(preparedStatement != null){
                    if(resultSet != null){
                        resultSet.close();
                    }
                    preparedStatement.close();
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public int execUpdateSQL(String sql) throws SQLException {
        return execUpdateSQL(sql,null);
    }

    @Override
    public int execUpdateSQL(String sql, List<Object> paramList) throws SQLException {
        return getPreparedStatement(sql,paramList,false,false).executeUpdate();
    }

    @Override
    public void createTable(Class<?> clazz) throws DoNotCreateException, SQLException {
        execUpdateSQL(PackagSQL.createTable(clazz));
    }

    @Override
    public void deleteTable(Class<?> clazz) throws DoNotCreateException, SQLException {
        execUpdateSQL(PackagSQL.deleteTable(clazz));
    }

    @Override
    public Object insert(String sql, List<Object> paramList, boolean isReturnPrimaryKey) throws SQLException, DatabaseCoreException {
        int execResult = execUpdateSQL(sql,paramList);
        if(execResult != 0){
            if(isReturnPrimaryKey){
                resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getObject(1);
            }else {
                return execResult;
            }
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
            // 设置主键
            ClassUtil.setFieldValues(t,field,insert(PackagSQL.insert(t),null,true));
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
        return execUpdateSQL(sql,paramList);
    }

    @Override
    public int delete(String sql) throws SQLException {
        return execUpdateSQL(sql);
    }

    @Override
    public <T> int delete(T t) throws SQLException {
        return execUpdateSQL(PackagSQL.delete(t));
    }

    @Override
    public <T> void delete(List<T> tList) throws SQLException {
        for (T t : tList){
            execUpdateSQL(PackagSQL.delete(t));
        }
    }

    @Override
    public int update(String sql, List<Object> paramList) throws SQLException {
        return execUpdateSQL(sql,paramList);
    }

    @Override
    public int update(String sql) throws SQLException {
        return execUpdateSQL(sql);
    }

    @Override
    public Map<String, Object> select(String sql, List<Object> paramList) throws SQLException {
        resultSet =  execQuerySQL(sql, paramList,true);
        resultSet.last();
        // 判断结果集是否大于1 如果是则抛出异常
        if(resultSet.getRow() > 1){
            throw new SQLException("期望获取结果为：1条数据，当前获取结果为："+resultSet.getRow()+"条数据！");
        }
        // 判断结果集是否等于0 如果是则返回空
        if(resultSet.getRow() == 0){
            return null;
        }
        resultSet.first();
        return ObjectRelationMapping.handleResultSetToMap(resultSet);
    }

    @Override
    public Map<String, Object> select(String sql) throws SQLException {
        resultSet =  execQuerySQL(sql,true);
        resultSet.last();
        // 判断结果集是否大于1 如果是则抛出异常
        if(resultSet.getRow() > 1){
            throw new SQLException("期望获取结果为：1条数据，当前获取结果为："+resultSet.getRow()+"条数据！");
        }
        // 判断结果集是否等于0 如果是则返回空
        if(resultSet.getRow() == 0){
            return null;
        }
        resultSet.first();
        return ObjectRelationMapping.handleResultSetToMap(resultSet);
    }

    @Override
    public List<Map<String, Object>> selectList(String sql, List<Object> paramList) throws SQLException {
        resultSet = execQuerySQL(sql, paramList,false);
        return ObjectRelationMapping.handleResultSetToMapList(resultSet);
    }

    @Override
    public List<Map<String, Object>> selectList(String sql) throws SQLException {
        resultSet = execQuerySQL(sql,false);
        return ObjectRelationMapping.handleResultSetToMapList(resultSet);
    }

    @Override
    public <T> T select(String sql, List<Object> paramList, Class<T> clazz) throws SQLException {
        try {
            return MapUtil.mapToObject(clazz,select(sql,paramList));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T select(String sql, Class<T> clazz) throws SQLException {
        return select(sql,null,clazz);
    }

    @Override
    public <T> List<T> selectList(String sql, List<Object> paramList, Class<T> clazz) throws SQLException {
        List<Map<String,Object>> mapList = selectList(sql, paramList);
        List<T> tList = new ArrayList<>();
        mapList.forEach(map -> {
            try {
                tList.add(MapUtil.mapToObject(clazz,map));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | IntrospectionException e) {
                e.printStackTrace();
            }
        });
        return tList;
    }

    @Override
    public <T> List<T> selectList(String sql, Class<T> clazz) throws SQLException {
        return selectList(sql,null,clazz);
    }

    /**
     * 获取声明
     * @param sql sql语句
     * @param paramList 参数列表
     * @param isQuery 是否是查询
     * @param isASingle 是否为单条
     * @return 参数
     */
    private PreparedStatement getPreparedStatement(String sql, List<Object> paramList,boolean isQuery,boolean isASingle) throws SQLException {
        // TODO 打印sql
        System.out.println(sql);
        // 获取声明，设置返回主键
        if(isQuery){
            preparedStatement = connection.prepareStatement(sql,
                    isASingle?ResultSet.TYPE_SCROLL_INSENSITIVE:ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }else {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        // 如果参数不为空 则封装参数
        if(paramList != null && !paramList.isEmpty()) {
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setObject(i + 1, paramList.get(i));
            }
        }
        return preparedStatement;
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
            connection.close();
        }
    }
}
