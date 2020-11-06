package org.taiji.framework.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.beans.Beans;
import org.taiji.framework.core.beans.annotation.Controller;

/**
 * 请求处理工厂
 */
public class RequestHandlerFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化映射
     */
    public void initHandler(){
        logger.info("开始初始化web");

        // 初始化映射
        doInitRequestHandler();
    }

    private void doInitRequestHandler(){
        logger.info("开始初始化映射");
        // 遍历bean，clntroller依赖于bean
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();
        Beans.getBeanInfoMap().forEach((key,value) ->{
            // 判断bean是否存在Controller注解
            if(value.getBeanClass().isAnnotationPresent(Controller.class)){
                // 将获取到的对象交由requestHandlerMapping进行初始化
                requestHandlerMapping.doInitRequestHandlerMapping(value.getBeanObject());
            }
        });
    }

}
