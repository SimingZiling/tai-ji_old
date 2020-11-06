package org.taiji.framework.core.beans;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.beans.annotation.Bean;
import org.taiji.framework.core.beans.annotation.Inject;
import org.taiji.framework.core.beans.config.BeanConfiguration;
import org.taiji.framework.core.beans.exception.BeanException;
import org.yang.localtools.util.ClassUtil;
import org.yang.localtools.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BeanFactory{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Bean信息列表
     */
    private final List<BeanInfo> beanInfos = new ArrayList<>();

    /**
     * 初始化bean
     */
    public void initBean(){
        logger.info("开始初始化Bean");

        // 扫描包
        doScanPackages();

        // 实例化对象
        doInstantiateObject();

        // 依赖注入
        doDependencyInjection();

    }

    /**
     * 进行依赖注入
     */
    private void doDependencyInjection(){
        logger.info("开始进行依赖注入");
        Beans.beanInfoMap.forEach((key,value) ->{
            // 获取到所有属性
            Field[] fields = value.getBeanClass().getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                // TODO 未实现set方法注入
                // 判断属性是否需要注入注解
                if(field.isAnnotationPresent(Inject.class)){
                    Inject inject = field.getAnnotation(Inject.class);
                    // 判断是否需要依赖注入
                    if(inject.required()){
                        try {
                            String beanName;
                            if(inject.value().equals("")){
                                beanName = field.getName();
                            }else {
                                beanName = inject.value();
                            }
                            if(!Beans.beanInfoMap.containsKey(beanName)){
                                throw new BeanException("没有名字为："+beanName+"的Bean！");
                            }

                            // 注入到对象中
                            ClassUtil.setFieldValues(value.getBeanObject(),field,Beans.beanInfoMap.get(beanName).getBeanObject());
                        } catch (BeanException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });
    }


    /**
     * 进行实例化对象
     */
    private void doInstantiateObject(){
        logger.info("开始进行Bean对象实例化");
        beanInfos.forEach(beanInfo -> {
            try {
                Class<?> beanClass = beanInfo.getBeanClass();
                // 实例化Bean对象
                Object beanObject = beanClass.getDeclaredConstructor().newInstance();
                String beanName = getBeanName(beanClass);
                if(Beans.beanInfoMap.containsKey(beanName)){
                    throw new BeanException("Bean"+beanName+"名称重复！");
                }
                beanInfo.setBeanObject(beanObject);
                // 添加到Beans的bean信息中
                Beans.beanInfoMap.put(beanName,beanInfo);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | BeanException e) {
                e.printStackTrace();
            }
        });
    }

    private String getBeanName(Class<?> beanClass){
        String beanName = (String) ClassUtil.getAnnotationValue(beanClass.getAnnotations(),Bean.class,"value");
        if(StringUtil.isNull(beanName)){
            // 获取接口类
            Class<?>[] interfacesClass = beanClass.getInterfaces();
            // 如果类存在接口则获取第一个接口名称为bean名称
            // TODO 暂未实现多继承的解决方案
            if(interfacesClass.length > 0){
                return StringUtil.initialLowercase(interfacesClass[0].getSimpleName());
            }
            return StringUtil.initialLowercase(beanClass.getSimpleName());
        }else {
            return beanName;
        }
    }



    /**
     * 扫描包，扫描完成后存放到beanInfos列表中，等待后续使用
     */
    private void doScanPackages() {
        logger.info("开始扫描包获取Bean");
        Set<Class<?>> beanClassList = ClassUtil.scanClases(BeanConfiguration.getScanPackages());
        beanClassList.forEach(beanClass ->{
            // 判断当clazz 不是注解的时候记录到bean中，并且迭代判断是否具有Bean注解
            if(!beanClass.isAnnotation() && ClassUtil.verifyClassAnnotation(beanClass, Bean.class,true)) {
                // 封装Beaninfo
                BeanInfo beanInfo = new BeanInfo();
                beanInfo.setBeanClass(beanClass);
                beanInfos.add(beanInfo);
            }
        });
    }

}
