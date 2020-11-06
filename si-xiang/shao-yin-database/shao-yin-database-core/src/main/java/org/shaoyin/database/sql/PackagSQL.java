package org.shaoyin.database.sql;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.annotation.Table;
import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.exception.DoNotCreateException;
import org.shaoyin.database.jdbc.conversion.TypeConversion;
import org.shaoyin.database.util.ColumnInfo;
import org.yang.localtools.exception.LocalToolsException;
import org.yang.localtools.util.ClassUtil;
import org.yang.localtools.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 封装SQL
 */
public class PackagSQL {

    private PackagSQL(){}
    

    /**
     * 获取备注
     * @param column 属性注解
     * @return 备注
     */
    private static String getComment(Column column){
        if(column == null || column.comment().equals("")){
            return "";
        }else {
            return "COMMENT "+column.comment();
        }
    }

    /**
     * 获取主键名称
     * @param column 属性
     * @param fieldName 属性名
     * @return 主键名
     */
    private static String getKeyName(Column column,String fieldName){
        // 判断是否为主键
        if(column != null && column.key()){
            // 判断别名是否存在
            if(!column.value().equals("")){
                return "`"+column.value()+"`";
            }else {
                return "`"+fieldName+"`";
            }
        }else {
            return "";
        }
    }

    /**
     * 获取自动增长
     * @param column 属性
     * @return 自动增长
     */
    private static String getAutoIncrement(Column column){
        if(column == null || !column.autoIncrement()){
            return "";
        }else {
            return "AUTO_INCREMENT";
        }
    }

    /**
     * 获取数类型
     * @param column 属性注解
     * @param fieldType 字段类型
     * @return 类型
     */
    private static String getType(Column column,Class<?> fieldType){
        if(column != null && !column.jdbcType().equals("")) {
            return column.jdbcType();
        }else {
            String jdbcDataType = TypeConversion.javaClassToJdbcType(fieldType);
            if (jdbcDataType != null) {
                return jdbcDataType;
            } else {
                // TODO 暂时没考虑外键情况 直接返回String类型
                return TypeConversion.javaClassToJdbcType(String.class);
            }
        }
    }

    /**
     * 获取Column信息
     * @return Column信息
     */
    private static String getColumnInfo(Column column,String fieldName,Class<?> fieldType){
        StringBuilder stringBuilder = new StringBuilder();

        // 设置字段名称 前后设置` 避免关键字
        stringBuilder.append("`").append(ColumnInfo.getFieldName(column, fieldName)).append("` ");
        // 设置类型
        String type = getType(column,fieldType);
        stringBuilder.append(type);
        // 设置长度
        stringBuilder.append(getLength(column,type)).append(" ");
        // 设置是否允许为空
        stringBuilder.append(getNotNull(column)).append(" ");
        // 设置是否自动增长
        stringBuilder.append(getAutoIncrement(column)).append(" ");
        // 设置备注
        stringBuilder.append(getComment(column)).append(" ");
        // 添加末尾的，
        stringBuilder.append(",");

        return stringBuilder.toString();
    }

    /**
     * 获取是否为空
     * @param column 属性注解
     * @return 返回是否为空
     */
    private static String getNotNull(Column column){
        if(column != null && (column.notNull() || column.key())){
            return "NOT NULL";
        }else {
            return "NULL";
        }
    }

    /**
     * 获取长度
     * @param column 属性注解
     * @return 返回长度
     */
    private static String getLength(Column column,String type){
        // 当column为空或者长度为0的时候
        if(column == null || column.length() == 0){
            if(type.equals(JDBCType.VARCHAR.getName())){
                return "(255)";
            }else {
                return "";
            }
        }else if (column.decimals() != 0){
            return "("+column.length()+","+column.decimals()+")";
        }else {
            return "("+column.length()+")";
        }
    }

    /**
     * 获取表名称
     * @param entity 实体
     * @return 表名称
     */
    private static String getTableName(Class<?> entity){
        Table table = entity.getAnnotation(Table.class);
        // 当table注解的value属性为空时表名为首字母小写的类名
        if(table.value().equals("")){
            // 获取类名并去掉包名将首字母小写
            return StringUtil.initialLowercase(entity.getName().substring(entity.getName().lastIndexOf(".") + 1));
        }else {
            return table.value();
        }
    }

    /**
     * 创建表
     * @param entity 实体
     * @return 创建表sql
     */
    public static String createTable(Class<?> entity) throws DoNotCreateException {
        if(entity.isAnnotationPresent(Table.class)){
            // 创建语句
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
            stringBuilder.append("`").append(getTableName(entity)).append("`(");
            // 获取属性
            Field[] fields = entity.getDeclaredFields();
            // 进行属性遍历
            List<String> keyNames = new ArrayList<>();
            Arrays.stream(fields).forEach(field -> {
                // 判断属性是否存在Column注解 如果存在则使用field中的Column，如果不存在则获取对应的get/is方法
                if (field.isAnnotationPresent(Column.class)){
                    // 通过属性获取Column
                    Column column = field.getAnnotation(Column.class);
                    // 获取Column信息
                    stringBuilder.append(getColumnInfo(column,field.getName(),field.getType()));
                    String keyName = getKeyName(column,field.getName());
                    if (!keyName.equals("")){
                        keyNames.add(keyName);
                    }
                }else {
                    try {
                        Method method = ClassUtil.getGetOrIsMethod(entity,field.getName());
                        // 如果方法中存在Column注解，则使用方法的注解信息，如果不存在则单独写
                        if(method.isAnnotationPresent(Column.class)){
                            // 通过方法获取Column
                            Column column = method.getAnnotation(Column.class);
                            // 获取Column信息
                            stringBuilder.append(getColumnInfo(column,field.getName(),field.getType()));
                            String keyName = getKeyName(column,field.getName());
                            if (!keyName.equals("")){
                                keyNames.add(keyName);
                            }
                        }else {
                            // 方法没有注解
                            // 获取Column信息
                            stringBuilder.append(getColumnInfo(null,field.getName(),field.getType()));
                        }
                    } catch (LocalToolsException e) {
                        // 不存在这个方法
                        // 判断属性是否为公有或者为受保护（这两种方式可以被外部类调用，作为实体类一定会被调用）
                        if(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())){
                            stringBuilder.append(getColumnInfo(null,field.getName(),field.getType()));
                        }
                    }
                }
            });
            stringBuilder.append("PRIMARY KEY (");
            // 遍历主键名称设置主键
            keyNames.forEach(keyName-> stringBuilder.append(keyName).append(", "));
            stringBuilder.deleteCharAt(stringBuilder.length()-2);
            // 封装结尾部分
            stringBuilder.append(")");
            stringBuilder.append(");");
            return stringBuilder.toString();
        }else {
            throw new DoNotCreateException(entity.getName()+" 该实体没有Table注解，不创建表！");
        }
    }

    /**
     * 删除表
     * @param entity 实体
     * @return 删除表sql
     */
    public static String deleteTable(Class<?> entity) throws DoNotCreateException {
        if(entity.isAnnotationPresent(Table.class)){
            return "DROP TABLE `" + getTableName(entity) + "`;";
        }else {
            throw new DoNotCreateException(entity.getName()+" 该实体没有Table注解，不删除表！");
        }
    }

    /**
     * 插入
     * @param t 对象
     * @return 插入sql语句
     */
    public static <T> String insert(T t){
        return insert(t,true);
    }

    private static String getFieldNameByField(Field field,Class<?> entity){
        StringBuilder stringBuilder = new StringBuilder();
        Column column = null;
        if(field.isAnnotationPresent(Column.class)){
            column = field.getAnnotation(Column.class);
            stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`");
        }else {
            try {
                Method method = ClassUtil.getGetOrIsMethod(entity,field.getName());
                if(method.isAnnotationPresent(Column.class)){
                    column = method.getAnnotation(Column.class);
                }
                stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`");
            } catch (LocalToolsException e) {
                if(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())){
                    stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`");
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 插入
     * @param t 对象
     * @param joint 是否拼接参数
     * @return 插入sql语句
     */
    public static <T> String insert(T t,boolean joint){
        // 开始构建语句
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(getTableName(t.getClass())).append("(");
        Field[] fields = t.getClass().getDeclaredFields();
        // 遍历属性
        StringBuilder tailStringBuilder = new StringBuilder("VALUES (");
//        Arrays.stream(fields).forEach(field ->{
        int i = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(t) == null) {
                    i += 1;
                    continue;
                }
                Column column = null;
                if (field.isAnnotationPresent(Column.class)) {
                    column = field.getAnnotation(Column.class);
                    stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`,");
                } else {
                    try {
                        Method method = ClassUtil.getGetOrIsMethod(t.getClass(), field.getName());
                        if (method.isAnnotationPresent(Column.class)) {
                            column = method.getAnnotation(Column.class);
                        }
                        stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`,");
                    } catch (LocalToolsException e) {
                        if (Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
                            stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`,");
                        }
                    }
                }
                if (joint) {
                    if (getType(column, field.getType()).equals("DATETIME")) {
                        tailStringBuilder.append("'").append(new Timestamp(((Date) field.get(t)).getTime())).append("',");
                    } else {
                        tailStringBuilder.append("'").append(field.get(t)).append("',");
                    }
                } else {
                    tailStringBuilder.append("?,");
                }
            } catch (IllegalAccessException ignored) {
            }
        }
//        });
        if(i==fields.length){
            try {
                throw new DatabaseCoreException("插入语句构建失败！==》"+t.getClass().getName()+"对象所有属性为空！");
            } catch (DatabaseCoreException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(") ");
        stringBuilder.append(tailStringBuilder);
        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(");");
        return stringBuilder.toString();
    }

    /**
     * 删除SQL
     * @param t 实体
     * @return 删除sql
     */
    public static <T> String delete(T t){
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");
        stringBuilder.append(getTableName(t.getClass()));
        stringBuilder.append(" WHERE ");
//        Arrays.stream(t.getClass().getDeclaredFields()).forEach(field -> {
        Field[] fields = t.getClass().getDeclaredFields();
        int i = 0;
        for (Field field : fields){
            field.setAccessible(true);
            try {
                if(field.get(t) == null){
                    i += 1;
                    continue;
                }
                Column column = null;
                if(field.isAnnotationPresent(Column.class)){
                    column = field.getAnnotation(Column.class);
                    stringBuilder.append("`").append(ColumnInfo.getFieldName(column, field.getName())).append("`");
                }else {
                    stringBuilder.append(getFieldNameByField(field,t.getClass()));
                }
                if(getType(column,field.getType()).equals("DATETIME")){
                    stringBuilder.append(" = '").append(new Timestamp(((Date) field.get(t)).getTime())).append("' AND ");
                }else {
                    stringBuilder.append(" = '").append(field.get(t)).append("' AND ");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
//        });
        }
        if(i==fields.length){
            try {
                throw new DatabaseCoreException("删除语句构建失败！==》"+t.getClass().getName()+"对象所有属性为空！");
            } catch (DatabaseCoreException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.delete(stringBuilder.length()-4,stringBuilder.length()).append(";");
        return stringBuilder.toString();
    }

    public static String select(Class<?> entity){
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        Arrays.stream(entity.getDeclaredFields()).forEach(field -> stringBuilder.append("`").append(getFieldNameByField(field,entity)).append("`,"));
        stringBuilder.deleteCharAt(stringBuilder.length()-1).append(" WHERE ");
        return stringBuilder.toString();
    }
}
