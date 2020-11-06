package org.taiji.framework.core.web.annotation;

import java.lang.annotation.*;

/**
 * 请求映射
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    // 请求地址
    String value() default "";

    // 请求方式
    RequestMethod[] method() default {};
}
