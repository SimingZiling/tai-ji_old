package org.shaoyin.orm.exception;

/**
 * 不创建异常
 */
public class DoNotCreateException extends Exception {
    public DoNotCreateException(String message) {
        super(message);
    }
}
