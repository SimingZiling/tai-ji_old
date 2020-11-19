package org.shaoyin.orm.session.impl;

import org.shaoyin.orm.model.Parameter;
import org.shaoyin.orm.session.SqlSession;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * 默认sql会话
 */
public class DefaultSqlSession implements SqlSession {

    /** 数据源 */
    private final DataSource dataSource;

    private PreparedStatement preparedStatement;

    private Statement statement;

    public DefaultSqlSession(DataSource dataSource) {
        // 设置数据源
        this.dataSource = dataSource;
    }

    @Override
    public int execUpdate(String sql,Parameter parameter,Parameter condition) {
//        try {
//            Object[] objects = null;
//            // 当参数不为空时，对参数进行处理
//            if(parameter != null){
//                // 当参数为集合时，重新构建sql，否则不重新构建sql
//                if(parameter.getParameterMap().isEmpty()){
//                    sql = anewBuilderSql(sql,parameter);
//                    objects = parameter.getParameterList().toArray();
//                }else {
//                    parameter.getParameterList().toArray();
//                }
//            }
//            statement = getStatement(sql,objects,false,false);
//            return 0;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return 0;
//        }
    }

    /**
     * 获取声明
     * @return 返回声明
     */
    private Statement getStatement(String sql,Object[] parameter, boolean isQuery, boolean isASingle) throws SQLException {

        // 判断是否是查询操作,如果是查询操作则需要考虑到后续查询中对结果集进行操作
        if(isQuery) {
            // 获取预编译的声明，此处抛出SQLException异常
            preparedStatement = dataSource.getConnection().prepareStatement(sql,
                    isASingle? ResultSet.TYPE_SCROLL_INSENSITIVE:ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }else {
            // 更新操作时，如果是插入语句则需要返回主键，设置：Statement.RETURN_GENERATED_KEYS
            preparedStatement = dataSource.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        }

        if(parameter != null) {
            // 遍历参数数组
            for (int i = 0; i < parameter.length; i++) {
                    preparedStatement.setObject(i + 1, parameter[i]);
                }
//            // 设置参数，如果参数为集合时则遍历参数集合的值
//            if (!parameter.getParameterMap().isEmpty()) {
//                // 遍历参数值
//                Object[] values = parameter.getParameterMap().values().toArray();
//                for (int i = 0; i < values.length; i++) {
//                    preparedStatement.setObject(i + 1, values[i]);
//                }
//            } else {
//                List<Object> values = parameter.getParameterList();
//                for (int i = 0; i < values.size(); i++) {
//                    preparedStatement.setObject(i + 1, values.get(i));
//                }
//            }
        }

        return preparedStatement;
    }

    /**
     * 带参数重新构建SQL语句
     * @param parameter 参数对象
     * @return 重新构建的sql
     */
    private String anewBuilderSql(String sql,Parameter parameter){
        // 当参数集合为空是不需要重新构建
        if(parameter.getParameterMap().isEmpty()){
           return sql;
        }else {
            // 开始重新构建sql语句
            StringBuilder sqlBuilder = new StringBuilder(sql);
            // 获取key列表并且进行遍历
            for (String key : parameter.getParameterMap().keySet()){
                sqlBuilder.append("`").append(key).append("' = ? AND ");
            }
            sqlBuilder.delete(sqlBuilder.length()-4,sqlBuilder.length());
            return sqlBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Parameter parameter = new Parameter();
        parameter.add("name","name2");
        parameter.add("email","email2");

        String sql = "SELECT * FROM user where ";
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(new DataSource() {
            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public Connection getConnection() throws SQLException {
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return null;
            }

            @Override
            public PrintWriter getLogWriter() throws SQLException {
                return null;
            }

            @Override
            public void setLogWriter(PrintWriter out) throws SQLException {

            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {

            }

            @Override
            public int getLoginTimeout() throws SQLException {
                return 0;
            }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                return null;
            }
        });
        System.out.println(defaultSqlSession.anewBuilderSql(sql,parameter));

//        Set<String> keySet = parameter.getParameterMap().keySet();
//        for (String key :keySet){
//            System.out.println(key);
//        }
//
//        // 遍历参数值
//        Object[] values = parameter.getParameterMap().values().toArray();
//        for (int i = 0; i < values.length;i++){
//            System.out.println(values[i]);
//        }
    }
}
