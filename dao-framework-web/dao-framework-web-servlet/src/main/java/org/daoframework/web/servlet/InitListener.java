package org.daoframework.web.servlet;


import org.dao.framework.beans.ModuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ModuleFactory moduleFactory = new ModuleFactory();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("程序开始");
        moduleFactory.doInitModules();
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("程序结束");
    }

}
