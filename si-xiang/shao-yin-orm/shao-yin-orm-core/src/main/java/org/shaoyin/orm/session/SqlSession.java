package org.shaoyin.orm.session;

import org.shaoyin.orm.model.Parameter;

import java.sql.Connection;

/**
 * sql会话
 */
public interface SqlSession {

    /**
     * 执行更新
     * 定义：增、删、改，均属于对sql进行更新
     * @param sql sql语句
     * @param parameter 参数
     * @return 受到影响的条数
     */
    int execUpdate(String sql, Parameter parameter);
}
