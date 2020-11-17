package org.shaoyin.orm.session;

import org.shaoyin.orm.config.Configuration;

/**
 * Sql会话工厂接口
 */
public interface SqlSessionFactory {

    /**
     * 开启SqlSession
     * @return SqlSession
     */
    SqlSession openSqlSession();

}
