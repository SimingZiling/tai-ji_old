package org.shaoyin.database.sql;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.annotation.Entity;
import org.shaoyin.database.annotation.Table;
import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.conversion.TypeConversion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


/**
 * 封装SQL
 */
public class PackagSQL {

    private PackagSQL(){};


    public static String createTable(Class<?> entity) throws DatabaseCoreException {
        if(entity.isAnnotationPresent(Entity.class)){
            // 开始创建语句
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");

            // 获取类名并去掉包名将首字母小写
            String entityName =entity.getName().substring(entity.getName().lastIndexOf(".") + 1).substring(0, 1).toLowerCase()+entity.getName().substring(entity.getName().lastIndexOf(".") + 1).substring(1);
            // 判断表是否存在Table注解，如果存在则验证Table不存在则使用首字母小写的类名
            if (entity.isAnnotationPresent(Table.class)){
                // 获取Table注解
                Table table = entity.getAnnotation(Table.class);
                // 如果表注解的值为空则依旧使用首字母小写的类名
                if(table.value().equals("")){
                    stringBuilder.append(entityName);
                }else{
                    stringBuilder.append(table.value());
                }
            }else {
                stringBuilder.append(entityName);
            }
            stringBuilder.append("(");
            // 获取属性
            Field[] fields = entity.getDeclaredFields();
            Arrays.stream(fields).forEach(field ->{
                // 如果属性存在注解则使用属性的注解
                if (field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);

                    // 如果别名为空则使用属性名
                    String alias = getAlias(column);
                    if(alias == null){
                        stringBuilder.append(field.getName());
                    }else {
                        stringBuilder.append(alias);
                    }
                    stringBuilder.append(" ");

                    // 获取jdbc数据类型
                    String jdbcDataType = getJdbcDataType(column);
                    if(jdbcDataType != null){
                        stringBuilder.append(jdbcDataType);
                    }else {
                        String jdbcType = TypeConversion.javaClassToJdbcType(field.getType());
                        if(jdbcType != null){
                            stringBuilder.append(jdbcType);
                        }else {
                            try {
                                throw new DatabaseCoreException(field.getType()+"该数据类型无法识别！");
                            } catch (DatabaseCoreException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (column.length() == 0){
                        String jdbcDefaultLength =
                    }
                    stringBuilder.append(" ");

                    // 获取是否为空
                    if(column.notNull()){
                        stringBuilder.append("NOT NULL");
                    }else {
                        stringBuilder.append("NULL");
                    }
                    stringBuilder.append(" ");

                }else {
                    // 获取属性名，将首字母大写
                    String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method method = null;
                    try {
                        // 获取get方法
                        method = entity.getMethod("get" + fieldName);
                    } catch (NoSuchMethodException | SecurityException ignored) {
                    }
                    try {
                        // 获取is方法
                        method = entity.getMethod("is" + fieldName);
                    } catch (NoSuchMethodException | SecurityException ignored) {
                    }
                    if (method == null){
                        // 判断属性是否为公有或者为受保护（这两种方式可以被外部类调用，作为实体类一定会被调用）
                        if(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())){
                            stringBuilder.append(field.getName());
                        }
                    }else {
                        if (method.isAnnotationPresent(Column.class)){
                            String alias = getAlias(field.getAnnotation(Column.class));
                            // 如果别名为空则使用属性名
                            if(alias == null){
                                stringBuilder.append(field.getName());
                            }else {
                                stringBuilder.append(alias);
                            }
                            // TODO
                        }else {
                            stringBuilder.append(field.getName());
                            // TODO
                        }
                    }
                }

//                stringBuilder.append("结束");
            });
            return stringBuilder.toString();
        }else {
            throw new DatabaseCoreException(entity.getName()+"不是实体！");
        }
    }


    /**
     * 获取别名
     * @param column 字段注解
     * @return 别名
     */
    private static String getAlias(Column column){
        if(column.value().equals("")){
            return null;
        }else {
            return column.value();
        }
    }

    /**
     * 获取数据类型
     * @return JDBC数据类型
     */
    private static String getJdbcDataType(Column column){
        if(column.jdbcType().equals("")){
            return null;
        }else {
            return column.jdbcType();
        }
    }

    public static void main(String[] args) throws DatabaseCoreException {
        System.out.println(PackagSQL.createTable(User.class));
//        System.out.println();
//        System.out.println(10 & 0X2);
    }
}
