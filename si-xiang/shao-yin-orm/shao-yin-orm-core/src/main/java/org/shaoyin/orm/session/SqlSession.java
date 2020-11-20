package org.shaoyin.orm.session;

import org.shaoyin.orm.model.Parameter;

/**
 * sql会话
 */
public interface SqlSession {

    /**
     * 执行更新
     * 定义：增、删、修均属对数据更新的操作
     * @param sql sql 语句
     * @return 影响的行数
     */
    int execUpdate(String sql);

    /**
     * 执行更新（带参数）
     * @param sql sql语句
     * @param parameter 参数
     * @return 执行结果
     */
    int execUpdate(String sql, Parameter parameter);

    /**
     * 执行查询
     * @param sql 查询的sq语句
     * @return 执行结果
     */
    int execQuery(String sql);

    /**
     * 执行查询（带参数）
     * @param sql sql语句
     * @param parameter 参数
     * @return 执行结果
     */
    int execQuery(String sql,Parameter parameter);

}
