package org.dao.framework.beans.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Module
public @interface Controller {

    String value() default "";

}
