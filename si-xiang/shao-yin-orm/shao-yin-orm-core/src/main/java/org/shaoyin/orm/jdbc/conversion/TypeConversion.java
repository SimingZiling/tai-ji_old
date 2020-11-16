package org.shaoyin.orm.jdbc.conversion;

import java.sql.JDBCType;
import java.util.Date;

public class TypeConversion {

    private  TypeConversion(){};

    /**
     * java类转jdbc数据类型
     * @param clazz java类
     * @return jdbc数据类型
     */
    public static String javaClassToJdbcType(Class<?> clazz){
        // 判断字符串
        if(clazz.equals(String.class)){
            return JDBCType.VARCHAR.getName();
        }
        // 判断整数
        if(clazz.equals(Integer.class) || clazz.equals(int.class)){
            return JDBCType.INTEGER.getName();
        }
        // 判断布尔类型
        if(clazz.equals(Boolean.class) || clazz.equals(boolean.class)){
            return JDBCType.BOOLEAN.getName();
        }
        // 判断长整形
        if (clazz.equals(Long.class) || clazz.equals(long.class)){
            return JDBCType.BIGINT.getName();
        }
        // 判断时间类型
        if (clazz.equals(Date.class)){
            return "DATETIME";
        }
        // 判断单精度浮点类型
        if ( clazz.equals(Float.class) || clazz.equals(float.class)){
            return JDBCType.FLOAT.getName();
        }
        // 判断双精度浮点类型
        if(clazz.equals(double.class) || clazz.equals(Double.class)){
            return JDBCType.DOUBLE.getName();
        }
        return null;
    }
}
