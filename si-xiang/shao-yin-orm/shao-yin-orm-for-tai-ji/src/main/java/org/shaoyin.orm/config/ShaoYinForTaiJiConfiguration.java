package org.shaoyin.orm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.beans.config.BeanConfiguration;

public class ShaoYinForTaiJiConfiguration implements org.taiji.framework.config.Configuration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void buildConfiguration(){
        logger.info("配置信息分发至ORM配置");
        // 添加指定扫描路径
        BeanConfiguration.setScanPackages("org.shaoyin.database.bean");
    }
}
