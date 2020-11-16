package org.shaoyin.orm.exception;

import org.taiji.framework.core.exception.TaiJiFrameworkCoreException;

/**
 * 数据库异常
 */
public class DatabaseException extends TaiJiFrameworkCoreException {

    public DatabaseException(String message) {
        super(message);
    }
}
