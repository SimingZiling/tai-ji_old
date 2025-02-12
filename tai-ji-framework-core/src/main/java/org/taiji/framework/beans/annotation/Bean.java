package org.taiji.framework.beans.annotation;

import java.lang.annotation.*;

/**
 * Bean注解，
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

    String value() default "";

}
