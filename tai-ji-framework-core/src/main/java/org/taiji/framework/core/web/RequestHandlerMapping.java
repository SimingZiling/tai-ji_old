package org.taiji.framework.core.web;

import org.taiji.framework.core.web.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求处理映射
 */
public class RequestHandlerMapping {

    /**
     * 请求处理Map
     */
    private static Map<String,RequestHandler> requestHandlerMap = new HashMap<>();

    /**
     * 获取url对应的请求处理
     * @param url 地址
     * @return 请求处理
     */
    public static RequestHandler getRequestHandler(String url) {
        return requestHandlerMap.get(url);
    }

    public static Map<String, RequestHandler> getRequestHandlerMap() {
        return requestHandlerMap;
    }

    public void doInitRequestHandlerMapping(Object beanObject){
        Class<?> beanClass = beanObject.getClass();
        // 判断bena是否有RequestMapping注解
        if(beanClass.isAnnotationPresent(RequestMapping.class)){
            String beanClassHandlerName;
            // 获取RequestMapping 从而获取硬着名称
            RequestMapping classRequestMapping = beanClass.getAnnotation(RequestMapping.class);
            if(!classRequestMapping.value().equals("")){
                beanClassHandlerName = "/"+classRequestMapping.value();
            }else {
                beanClassHandlerName = "/"+beanClass.getSimpleName();
            }
            // 获取方法列表，请求最终是执行到方法的
            Method[] modules = beanClass.getMethods();
            Arrays.stream(modules).forEach(method -> {
                // 判断方法是否有RequestMapping注解，如果没有则表示不是一个映射
                if(method.isAnnotationPresent(RequestMapping.class)){
                    String methodHandlerName;
                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                    if(!methodRequestMapping.value().equals("")){
                        methodHandlerName = "/"+methodRequestMapping.value();
                    }else {
                        methodHandlerName = "/"+method.getName();
                    }
                    // 封装映射
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.setObject(beanObject);
                    requestHandler.setMethod(method);
                    // 封装请求头
                    requestHandler.setRequestMethods(methodRequestMapping.method());
                    String url = (beanClassHandlerName+methodHandlerName).replaceAll("/+","/");
                    // 去掉url中的空格
                    url = url.replaceAll("\\s+","");
                    requestHandlerMap.put(url,requestHandler);
                }
            });
        }
    }
}
