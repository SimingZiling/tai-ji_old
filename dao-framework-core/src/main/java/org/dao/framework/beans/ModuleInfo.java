package org.dao.framework.beans;

/**
 * 组件信息
 */
public class ModuleInfo {

    /**
     * 组件的类
     */
    private Class<?> clazz;

    /**
     * 组件对象
     */
    private Object object;

    /**
     * 获取组件类
     * @return 组件类
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * 设置组件类
     * @param clazz 组件类
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取组件对象
     * @return 组件对象
     */
    public Object getObject() {
        return object;
    }

    /**
     * 设置组件对象
     * @param object 组件对象
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * 获取组件类名称
     * @return 组件类名称
     */
    public String getClassName(){
        return this.clazz.getName();
    }
}
