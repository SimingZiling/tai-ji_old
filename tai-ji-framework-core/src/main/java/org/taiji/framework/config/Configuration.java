package org.taiji.framework.config;

import org.taiji.framework.config.exception.ApplicationConfigurationException;

/**
 * 配置接口，统一配置，所有配置文件包括模块配置文件实现该配置接口
 */
public interface Configuration {

    /**
     * 构建配置，所有配置实现该接口方法
     */
    void buildConfiguration() throws ApplicationConfigurationException;

}
