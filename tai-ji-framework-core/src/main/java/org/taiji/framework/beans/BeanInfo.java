package org.taiji.framework.beans;

/**
 * Bean信息
 */
public class BeanInfo {

    /**
     * Bean的类
     */
    private Class<?> beanClass;

    /**
     * Bean对象
     */
    private Object beanObject;

    /**
     * 获取bean类
     * @return bean类
     */
    public Class<?> getBeanClass() {
        return beanClass;
    }

    /**
     * 设置Bean类
     * @param beanClass bean类
     */
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取Bean对象
     * @return bean对象
     */
    public Object getBeanObject() {
        return beanObject;
    }

    /**
     * 设置bean对象
     * @param beanObject 对象
     */
    public void setBeanObject(Object beanObject) {
        this.beanObject = beanObject;
    }

    /**
     * 获取bean名称
     * @return bena名称
     */
    public String getBeanClassName(){
        return beanClass.getName();
    }
}
