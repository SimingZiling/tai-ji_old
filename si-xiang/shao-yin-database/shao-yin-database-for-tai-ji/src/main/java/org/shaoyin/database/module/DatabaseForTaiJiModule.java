package org.shaoyin.database.module;

import org.shaoyin.database.jdbc.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.module.Module;

public class DatabaseForTaiJiModule implements Module {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() {
        logger.info("开始加载ORM模块");
//        // 加载连接池
        ConnectionPool.loadConnectionPool();
    }

    //  TODO 注入问题
}
