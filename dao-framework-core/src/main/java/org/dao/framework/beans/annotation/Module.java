package org.dao.framework.beans.annotation;

import java.lang.annotation.*;

/**
 * 组件注解，将java对象实例化成为组件
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {

    // bean名称
    String value() default "";

}
