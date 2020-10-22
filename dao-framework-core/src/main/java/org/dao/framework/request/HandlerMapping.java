package org.dao.framework.request;



import org.dao.framework.request.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 映射处理
 */
public class HandlerMapping {


    private static Map<String, Handler> handlerMap = new HashMap<>();

    public static Handler getHandler(String url) {
        return handlerMap.get(url);
    }

    /**
     * 初始化映射
     * @param object bean对象
     */
    public void doInitHandlerMapping(Object object) {
        Class<?> clazz = object.getClass();
        // 判断类是否具有RequestMapping注解
        if(clazz.isAnnotationPresent(RequestMapping.class)){
            String classRequestMappingNmae;
            // 获取类的RequestMapping 从而获取映射名称
            RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
            if(!classRequestMapping.value().equals("")){
                classRequestMappingNmae = "/"+classRequestMapping.value();
            }else {
                classRequestMappingNmae = "/"+clazz.getSimpleName();
            }
            // 获取clazz中的方法列表
            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                // 判断方法是否具有RequestMapping
                if(method.isAnnotationPresent(RequestMapping.class)){
                    String methodRequestMappingNmae;
                    // 获取方法的RequestMapping 从而获取映射名称
                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                    if(!methodRequestMapping.value().equals("")){
                        methodRequestMappingNmae = "/"+methodRequestMapping.value();
                    }else {
                        methodRequestMappingNmae = "/"+method.getName();
                    }
                    // 封装映射
                    Handler handler = new Handler();
                    handler.setObject(object);
                    handler.setMethod(method);
                    // 封装请求头
                    handler.setRequestMethods(methodRequestMapping.method());
                    String url = (classRequestMappingNmae+methodRequestMappingNmae).replaceAll("/+","/");
                    handlerMap.put(url,handler);
                }
            }
        }
    }

}
