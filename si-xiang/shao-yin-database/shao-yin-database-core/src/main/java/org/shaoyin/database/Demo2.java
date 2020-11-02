package org.shaoyin.database;

import org.shaoyin.database.sql.User;
import org.yang.localtools.util.MapUtil;

import java.beans.IntrospectionException;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Demo2 {

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @BInherited
    @interface AInherited {
        String value() default "";
    }

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Inherited // 声明注解具有继承性
    @interface BInherited {
        String value() default "";
    }

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
            // 未声明注解具有继承性
    @interface CInherited {
        String value() default "";
    }

//    @AInherited("《？？AInherited")
//    @BInherited("=？？BInherited")
//    @CInherited("*？？CInherited")
//    class SuperClass {
//    }
//
    @BInherited("》？？BInherited")
    class ChildClass {
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException {
//        Annotation[] annotations = ChildClass.class.getAnnotations();
//        System.out.println(ChildClass.class.isAnnotationPresent(BInherited.class));
//        AInherited aInherited = ChildClass.class.getAnnotation(AInherited.class);
//        System.out.println(aInherited.value());
        // output: [@annotations.InheritedTest1$AInherited(value=父类的AInherited), @annotations.InheritedTest1$BInherited(value=子类的BInherited)]
        Map<String,Object> map = new HashMap<>();
        map.put("birthday","2020-10-30 14:45:05.0");
        map.put("name","中文");
        map.put("email_s","啥也不是");
        map.put("id_s","11");
        map.put("ints",0.1);
        map.put("sha",0.1);


        System.out.println(MapUtil.mapToObject(User.class,map));
    }

}
