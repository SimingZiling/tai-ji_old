package org.taiji.framework.web.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.ApplicationStart;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("程序开始启动");
        ApplicationStart applicationStart = new ApplicationStart();
        applicationStart.run();
        logger.info("程序启动完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("程序结束");
    }

}
