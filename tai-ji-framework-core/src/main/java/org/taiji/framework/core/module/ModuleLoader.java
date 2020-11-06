package org.taiji.framework.core.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 模块加载器，负责各个模块加载
 */
public class ModuleLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Set<Class<?>> moduleClases = new HashSet<>();

    /**
     * 加载
     */
    public void load(){
        logger.info("开始加载模块");
        ServiceLoader<Module> serviceLoader = ServiceLoader.load(Module.class);
        serviceLoader.forEach(Module::init);
    }

}
