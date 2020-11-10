package org.taiji.framework.beans.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
public @interface Controller {

    String value() default "";

}
