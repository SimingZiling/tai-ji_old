package org.shaoyin.orm.session.impl;

import org.shaoyin.orm.model.Parameter;
import org.shaoyin.orm.session.SqlSession;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 默认sql会话
 */
public class DefaultSqlSession implements SqlSession {


    @Override
    public int execUpdate(String sql) {

        return execUpdate(sql,null);
    }

    @Override
    public int execUpdate(String sql, Parameter parameter) {
        /* 更新存在三种方法
           1、添加 INSERT INTO `user`(字段，字段) VALUES (值，值);
           2、删除 DELETE FROM `user` WHERE 字段= 值;
           3、更新 UPDATE `user` SET 字段 = 值 ,字段 = 值 WHERE 字段 = 值;
         */
        return 0;
    }

    @Override
    public int execQuery(String sql) {
        return execQuery(sql,null);
    }

    @Override
    public int execQuery(String sql, Parameter parameter) {
        return 0;
    }

    private String anewBuilderSql(String sql,Parameter parameter){
        StringBuilder sqlBuilder = new StringBuilder(sql);
        // 当参数不为空，并且参数状态不为list时对sql进行重新构建
        if(parameter != null && !parameter.getType()){
            Map<String,Object> parameterMap = parameter.getParameterMap();
            // 当参数不为空时
            if(!parameterMap.isEmpty()){
                // 遍历参数并且封装语句，将参数值添加到参数列表中
                for (Map.Entry<String,Object> entry: parameterMap.entrySet()){
                    sqlBuilder.append("`").append(entry.getKey()).append("' = ? , ");
                    parameter.add(entry.getValue());
                }
                sqlBuilder.delete(sqlBuilder.length()-2,sqlBuilder.length());
            }
            Map<String,Object> conditionMap = parameter.getConditionMap();
            if(!conditionMap.isEmpty()){
                sqlBuilder.append(" WHERE ");
                // 遍历条件并且封装语句，将条件值添加到参数列表中
                for (Map.Entry<String,Object> entry: conditionMap.entrySet()){
                    sqlBuilder.append("`").append(entry.getKey()).append("' = ? AND ");
                    parameter.add(entry.getValue());
                }
                sqlBuilder.delete(sqlBuilder.length()-4,sqlBuilder.length());
            }


        }
        return sqlBuilder.toString();
    }

    private Statement getStatement(String sql,Parameter parameter){
        return null;
    }

    public static void main(String[] args) {
        Parameter parameter = new Parameter();
        parameter
                .add("aa",22)
                .add("aa2",22)
                .addCondition("221","2222")
                .addCondition("2s21","22212");
//                .add("222")
//                .add("33");
        String sql = "SELECT * FROM user";
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession();

        System.out.println(defaultSqlSession.anewBuilderSql(sql,parameter));


    }
}
