package org.yang.localtools.util;




import org.yang.localtools.exception.LocalToolsException;
import org.yang.localtools.util.annotation.Alias;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * map转换
 */
public class MapUtil {

    private MapUtil(){}

    /**
     * Map转String
     * @param mapParam map参数
     * @param keyConnector 键值对连接
     * @param paramConnector 参数连接
     * @return String信息
     */
    public static String mapToString(Map<String,Object> mapParam,String keyConnector,String paramConnector){
        StringBuilder stringBuilder = new StringBuilder();
        // 遍历出map的Key与Value并进行拼接
        // TODO 验证属性是否为基础类型，不为基础类型则再次进行转换
        for (Map.Entry<String,Object> entry :mapParam.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(keyConnector);
            stringBuilder.append(entry.getValue());
            stringBuilder.append(paramConnector);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() -1);
        return stringBuilder.toString();
    }

    /**
     * Map转对象
     * @param clazz 类
     * @param values map值
     * @param <T> 对象
     * @return 对象
     */
    public static <T> T mapToObject(Class<T> clazz,Map<String, Object> values) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        // 实例化对象
        T t = clazz.getDeclaredConstructor().newInstance();
        // 获取属性列表(获取属性包含私有属性)
        Field[] fields = clazz.getDeclaredFields();
        // 遍历属性
        for(Field field : fields) {
            // 获取别名属性
            Object fieldName = ClassUtil.getAnnotationValue(field.getAnnotations(), Alias.class, "value");
            // 如果fieldName为空则从方法中获取值 否则fieldName = field.getName();
            if (fieldName == null || fieldName.equals("")) {
                try {
                    // 获取get或者is方法
                    Method method = ClassUtil.getGetOrIsMethod(clazz, field.getName());
                    // 获取方法注解
                    fieldName = ClassUtil.getAnnotationValue(method.getDeclaredAnnotations(), Alias.class, "value");
                } catch (LocalToolsException ignored) {
                    fieldName = field.getName();
                }
            }
            if(fieldName == null || fieldName.equals("")){
                fieldName = field.getName();
            }
            // TODO 此处用类加载器判断是否为JAVA类型，未知是否存在问题
            Object value = values.get(String.valueOf(fieldName));
            if(value != null) {
                // 当属性类型为java基础类型时 直接加载
                if (field.getType().getClassLoader() == null) {
                    ClassUtil.setFieldValues(t, field, value);
                } else {
                    ClassUtil.setFieldValues(t, field, mapToObject(field.getType(), (Map<String, Object>) value));
                }
            }

//        });
        }
        return t;
    }

    /**
     * 对象转Map
     * @param object 对象
     * @return mao数据
     */
    public static Map<String,Object> objectToMap(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = object.getClass();
        for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz).getPropertyDescriptors()){
            if(propertyDescriptor.getName().equals("class")){
                continue;
            }

            // 获取方法注解
            if(propertyDescriptor.getReadMethod().isAnnotationPresent(Alias.class)){
                Method method = propertyDescriptor.getReadMethod();
                Alias alias = method.getAnnotation(Alias.class);
                map.put(alias.value(),method.invoke(object));
            }else
                // 获取属性注解
                if (clazz.getDeclaredField(propertyDescriptor.getName()).isAnnotationPresent(Alias.class)){
                    Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                    Alias alias = field.getAnnotation(Alias.class);
                    field.setAccessible(true);
                    map.put(alias.value(),field.get(object));
                }else
                    // 直接通过属性获取
                    {
                        Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                        field.setAccessible(true);
                        map.put(propertyDescriptor.getName(),field.get(object));
            }
        }
        return map;
    }

}
