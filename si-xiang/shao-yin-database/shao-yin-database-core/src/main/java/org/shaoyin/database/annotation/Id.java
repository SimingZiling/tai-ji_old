package org.shaoyin.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键标识
 */
@Target({ ElementType.METHOD,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

}
