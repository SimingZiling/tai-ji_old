package org.yang.localtools.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 别名注解
 */
@Target({ ElementType.METHOD,ElementType.FIELD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {

    /**
     * （可选） 字段名称
     * @return 别名
     */
    String value() default "";

}
