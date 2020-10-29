package org.shaoyin.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.JDBCType;

/**
 * 列属性
 */
@Target({ ElementType.METHOD,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 名称 默认为空
     * @return 名称
     */
    String value() default "";

    /**
     * 是否为空 默认为否 允许为空
     * @return 是否为空
     */
    boolean notNull() default false;

    /**
     * 字段长度 默认为0
     * @return 字段长度
     */
    int length() default 0;

    /**
     * jdbc类型
     * @return 类型
     */
    String jdbcType() default "";

    int decimals() default 0;

    /**
     * 自动增长
     * @return 默认为否
     */
    boolean autoIncrement() default false;

    /**
     * 备注
     * @return 备注信息
     */
    String comment() default "";

    /**
     * 主键
     * @return 是否为主键
     */
    boolean key() default false;

}
