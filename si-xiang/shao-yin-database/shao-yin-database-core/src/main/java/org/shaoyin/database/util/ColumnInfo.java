package org.shaoyin.database.util;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.exception.DatabaseCoreException;
import org.yang.localtools.exception.LocalToolsException;
import org.yang.localtools.util.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ColumnInfo {

    /**
     * 获取字段名
     * @param column 属性注解
     * @param fieldName 字段名
     * @return 字段名
     */
    public static String getFieldName(Column column, String fieldName){
//        if(column == null || column.value().equals("")){
//            return "`"+fieldName+"`";
//        }else {
//            return "`"+column.value()+"`";
//        }
        if(column == null || column.value().equals("")){
            return fieldName;
        }else {
            return column.value();
        }
    }

    /**
     * TODO 只获取第一个
     * 获取key名称
     * @param clazz 类
     * @param isJaveFieldName 是否为java字段名称
     * @return key名称
     */
    public static String getKeyName(Class<?> clazz,boolean isJaveFieldName) throws LocalToolsException, DatabaseCoreException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                if(column.key()){
                    if(isJaveFieldName) {
                        return getFieldName(null, field.getName());
                    }else {
                        return getFieldName(column, field.getName());
                    }
                }
            }else {
                Method method = ClassUtil.getGetOrIsMethod(clazz, field.getName());
                if (method.isAnnotationPresent(Column.class)){
                    Column column = method.getAnnotation(Column.class);
                    if(isJaveFieldName) {
                        return getFieldName(null, field.getName());
                    }else {
                        return getFieldName(column, field.getName());
                    }
                }
            }
        }
        throw new DatabaseCoreException("没有主键");
    }

    /**
     * 获取key名称
     * @param clazz 类
     * @return key名称
     */
    public static String getKeyName(Class<?> clazz) throws LocalToolsException, DatabaseCoreException {
        return getKeyName(clazz,false);
    }
}
