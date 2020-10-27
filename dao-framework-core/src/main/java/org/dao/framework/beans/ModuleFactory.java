package org.dao.framework.beans;

import org.dao.framework.beans.annotation.Controller;
import org.dao.framework.beans.annotation.Inject;
import org.dao.framework.beans.annotation.Module;
import org.dao.framework.beans.annotation.Service;
import org.dao.framework.config.Configuration;
import org.dao.framework.exception.DaoFrameworkCoreException;
import org.dao.framework.request.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yang.localtools.util.ClassUtil;
import org.yang.localtools.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * 组件工厂
 */
public class ModuleFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化模块
     */
    public void doInitModules(){

        // 加载配置文件
        new Configuration().doLoadApplicationConfig();

        // 扫描包
        try {
            scanPackages();
        } catch (DaoFrameworkCoreException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }

        // 实例化对象
        doInstance();

        // 依赖注入
        doAutowirted();
    }

    /**
     * 开始进行依赖注入
     */
    private void doAutowirted() {
        // TODO 验证注入的组件是否存在
        logger.info("开始进行依赖注入");
        Modules.moduleInfoMap.forEach((key, value) -> {
            // 获取到所有的属性
            Field[] fields = value.getClazz().getDeclaredFields();
            for (Field field : fields){
                // 设置允许设置私有属性
                field.setAccessible(true);
                // 判断是否有Autowirted注解
                if(field.isAnnotationPresent(Inject.class)){
                    Inject inject = field.getAnnotation(Inject.class);
                    // 判断该属性是否需要注入
                    if(inject.required()){
                        try {
                            // 判断Autowirted注解是否有名称，如果有则使用注解名称注入，没有则使用属性名称注入
                            if(!inject.value().equals("")){
                                if(!Modules.moduleInfoMap.containsKey(inject.value())){
                                    throw new DaoFrameworkCoreException("没有名字为："+inject.value()+" 的组件，问题出现在"+value.getClassName());
                                }
                                field.set(value.getObject(),Modules.moduleInfoMap.get(inject.value()).getObject());
                            } else {
                                if(!Modules.moduleInfoMap.containsKey(field.getName())){
                                    throw new DaoFrameworkCoreException("没有名字为："+field.getName()+" 的Bean，问题出现在"+value.getClassName());
                                }
                                field.set(value.getObject(),Modules.moduleInfoMap.get(field.getName()).getObject());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 进行实例化
     */
    private void doInstance() {
        logger.info("开始进行实例化");
        for (ModuleInfo moduleDefinition : Modules.moduleInfos){
            try {
                Class<?> clazz = moduleDefinition.getClazz();
                // 实例化对象
                Object object = clazz.getDeclaredConstructor().newInstance();
                // 获取bean名称
                String modulenName = getModuleName(clazz,object);
                // 判断beanNmae中是否重复
                if(Modules.moduleInfoMap.containsKey(modulenName)){
                    throw new DaoFrameworkCoreException("组件:"+modulenName+" 名称重复！");
                }
                moduleDefinition.setObject(object);
                Modules.moduleInfoMap.put(modulenName,moduleDefinition);
            } catch (DaoFrameworkCoreException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取module名称
     * @param clazz 类
     * @param object 对象
     * @return module名称
     */
    private String getModuleName(Class<?> clazz, Object object) {

        String modulenName = StringUtil.initialLowercase(clazz.getSimpleName());
        // 判断注解是否有名字
        if(clazz.isAnnotationPresent(Controller.class)){
            Controller controller = clazz.getAnnotation(Controller.class);
            if(!controller.value().equals("")) {
                modulenName = controller.value();
            }
            // TODO 看看是否能够将初始化HandlerMapping 的操作踢出去做
            HandlerMapping handlerMapping = new HandlerMapping();
            handlerMapping.doInitHandlerMapping(object);
        }else if(clazz.isAnnotationPresent(Service.class)) {
            Service service = clazz.getAnnotation(Service.class);
            if (!service.value().equals("")) {
                modulenName = service.value();
            }
            // TODO 未考虑多继承
            else {
                Class<?>[] clazzs = clazz.getInterfaces();
                if (clazzs.length > 0) {
                    modulenName = StringUtil.initialLowercase(clazz.getInterfaces()[0].getSimpleName());
                }
            }
        }else if(clazz.isAnnotationPresent(Module.class)){
            Module module = clazz.getAnnotation(Module.class);
            if(!module.value().equals("")) {
                modulenName = module.value();
            }
        }
        return modulenName;
    }

    /**
     * 从配置文件获取包路径并且进行扫描
     */
    private void scanPackages() throws DaoFrameworkCoreException {
        logger.info("开始扫描包");
        // 判断scanPackages参数是否存在，如果不存在则无法进行包扫描
        if(!Configuration.verifyConfig("scanPackages")){
            throw new DaoFrameworkCoreException("scanPackages参数不存在！");
        }

        // 获取配置文件中更需要扫描的包
        String[] packages = String.valueOf(Configuration.getConfig("scanPackages")).split(",");
        Set<Class<?>> clazzs = ClassUtil.scanClases(packages);
        clazzs.forEach(clazz ->{
            // 判断当clazz 不是注解的时候记录到bean中，并且迭代判断是否具有Bean注解
            if(!clazz.isAnnotation() && ClassUtil.verifyAnnotation(clazz, Module.class,true)) {
                // 封装BeanDefinition
                ModuleInfo moduleInfo = new ModuleInfo();
                moduleInfo.setClazz(clazz);
                Modules.moduleInfos.add(moduleInfo);
            }
        });
    }
}
