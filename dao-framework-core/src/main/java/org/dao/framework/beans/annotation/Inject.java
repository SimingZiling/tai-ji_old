package org.dao.framework.beans.annotation;

import java.lang.annotation.*;

/**
 * 注入，对封装的组件进行注入
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

    // 是否进行依赖注入，默认为是
    boolean required() default true;

    // 依赖注入的bean名称
    String value() default "";
}
