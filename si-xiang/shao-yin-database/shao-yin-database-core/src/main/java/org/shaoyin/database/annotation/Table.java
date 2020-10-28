package org.shaoyin.database.annotation;

import org.yang.localtools.information.CharacterSet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * 表名称 默认为空表示类名为表
     * @return 表名称
     */
    String value() default "";

    /**
     * 字符集 默认UTF8
     * @return 字符集
     */
    CharacterSet characterSet() default CharacterSet.UTF8;
}
