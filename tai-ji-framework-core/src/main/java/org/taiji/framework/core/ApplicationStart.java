package org.taiji.framework.core;

import org.taiji.framework.beans.BeanFactory;
import org.taiji.framework.beans.Beans;
import org.taiji.framework.beans.config.BeanConfiguration;
import org.taiji.framework.config.ApplicationConfiguration;
import org.taiji.framework.config.ConfigurationFactory;
import org.taiji.framework.config.exception.ApplicationConfigurationException;
import org.taiji.framework.core.module.ModuleLoader;
import org.taiji.framework.core.web.RequestHandlerFactory;
import org.taiji.framework.core.web.RequestHandlerMapping;

/**
 * 程序启动
 */
public class ApplicationStart {

    public void run() {
//        // 初始化配置文件
//        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
//        applicationConfiguration.initConfiguration();
//
//        // 初始化bean
//        BeanFactory beanFactory = new BeanFactory();
//        beanFactory.initBean();
//
//        // 初始化web
//        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory();
//        requestHandlerFactory.initHandler();
//
//        // 加载模块
//        ModuleLoader moduleLoader = new ModuleLoader();
//        moduleLoader.load();

        // 加载配置文件完成初始化配置，等待配置分发至具体模块
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        applicationConfiguration.doLoudConfiguration();

        // 进行配置分发
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.distribution();
        } catch (ApplicationConfigurationException e) {
            e.printStackTrace();
        }

        // 加载Bean
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.doLoadBean();

        // 初始化web
        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory();
        requestHandlerFactory.initHandler();


        // 加载模块
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.load();
    }

}
