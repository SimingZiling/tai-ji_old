package org.taiji.framework.core.module;

import java.util.*;

/**
 * 模块加载器，负责各个模块加载
 */
public class ModuleLoader {

    private Set<Class<?>> moduleClases = new HashSet<>();

    /**
     * 加载
     */
    public void load(){
        ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class);
        for (Module module : serviceLoader){
//            moduleClases.add(module.getClass());
            module.init();
        }
    }

    public static void main(String[] args){
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.load();
    }


//    public static void main(String[] args) {
//        getAllClassByInterface(Module.class).forEach(aClass -> {
//           System.out.println(aClass.getName());
//        });
//    }
}
