package org.shaoyin.database.exception;

import org.shaoyin.database.session.Session;

/**
 * 数据库核心异常
 */
public class DatabaseCoreException extends Exception{

    public DatabaseCoreException(String message) {

        super(message);
    }
}
