package org.shaoyin.orm.exception;

/**
 * 数据库配置异常处理类，继承异常
 */
public class DatabaseConfigException extends Exception{

    public DatabaseConfigException(String message) {
        super(message);
    }
}
