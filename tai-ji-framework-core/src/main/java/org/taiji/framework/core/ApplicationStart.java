package org.taiji.framework.core;

import org.taiji.framework.core.beans.BeanFactory;
import org.taiji.framework.core.confog.ApplicationConfiguration;
import org.taiji.framework.core.module.ModuleLoader;
import org.taiji.framework.core.web.RequestHandlerFactory;

/**
 * 程序启动
 */
public class ApplicationStart {

    public void run(){
        // 初始化配置文件
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        applicationConfiguration.initConfiguration();

        // 初始化bean
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.initBean();

        // 初始化web
        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory();
        requestHandlerFactory.initHandler();

        // 加载模块
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.load();
    }

}
