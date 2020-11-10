package org.taiji.framework.config.exception;

import org.taiji.framework.core.exception.TaiJiFrameworkCoreException;

/**
 * 应用程序配置异常
 */
public class ApplicationConfigurationException extends TaiJiFrameworkCoreException {

    public ApplicationConfigurationException(String message) {
        super(message);
    }
}
