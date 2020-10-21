package org.dao.framework.beans.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Module
public @interface Service {

    // bean名称
    String value() default "";

}
