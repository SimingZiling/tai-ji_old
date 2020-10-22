package org.dao.framework.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件
 */
public class Modules {
    /**
     * 组件信息列表
     */
    protected static List<ModuleInfo> moduleInfos = new ArrayList<ModuleInfo>();

    /**
     * 组件信息集合，key为组件名称
     */
    protected static Map<String,ModuleInfo> moduleInfoMap = new HashMap<>();

    /**
     * 获取组件对象
     * @param modulesName 组件名称
     * @return 组件对象
     */
    public static Object getModulesObject(String modulesName){
        return moduleInfoMap.get(modulesName).getObject();
    }
    
    
}
