package org.shaoyin.orm.exception;

import org.shaoyin.orm.session.Session;

/**
 * 数据库核心异常
 */
public class DatabaseCoreException extends Exception{

    public DatabaseCoreException(String message) {

        super(message);
    }
}
