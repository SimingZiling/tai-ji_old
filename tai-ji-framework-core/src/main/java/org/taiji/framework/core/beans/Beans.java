package org.taiji.framework.core.beans;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean
 */
public class Beans {


    /**
     * benaMap集合
     */
    protected static Map<String,BeanInfo> beanInfoMap = new HashMap<>();

    /**
     * 通过bean名称获取bean对象
     * @param benaName bean名称
     * @return bean对象
     */
    public Object getBeansObject(String benaName){
        return beanInfoMap.get(benaName).getBeanObject();
    }

    public static Map<String,BeanInfo> getBeanInfoMap(){
        return beanInfoMap;
    }
}
