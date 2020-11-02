package org.shaoyin.database;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.jdbc.config.DatabaseConfiguration;
import org.shaoyin.database.session.Session;
import org.shaoyin.database.session.SessionFactory;
import org.shaoyin.database.session.impl.SessionFactoryImpl;
import org.shaoyin.database.sql.PackagSQL;
import org.shaoyin.database.sql.User;
import org.yang.localtools.exception.LocalToolsException;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws LocalToolsException, DatabaseCoreException, SQLException {
        // 构建数据库配置
        DatabaseConfiguration.setDriverClass("com.mysql.cj.jdbc.Driver");
        DatabaseConfiguration.setUrl("jdbc:mysql://132.232.19.205:3306/demo?autoReconnect=true&useSSL=false&serverTimezone=GMT%2b8&characterEncoding=utf8&allowMultiQueries=true");
        DatabaseConfiguration.setPassword("angel83528358");
        DatabaseConfiguration.setUser("root");

        SessionFactory sessionFactory = new SessionFactoryImpl();
        Session session =  sessionFactory.openSession();
        User user = new User();
//        user.setName("中文");
//        user.setEmail("啥也不是");
//        user.birthday = new Date();
////        PackagSQL.insert(user);
////        System.out.println(session.insert(PackagSQL.insert(user),null,true));
        User user2 = new User();
        user2.setId(8);
        user2.setName("中文2");
        user2.setEmail("啥也不是2");
//        user2.birthday = new Date();
//
//
//
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(user2);
////        User user3 = new User();
////        users.add(user3);
//        System.out.println(Arrays.asList(session.insert(users)).toString());
        List<Object> list = new ArrayList<>();
//        list.add("中文2");
        list.add("11");
//        list.add("中文");

////        System.out.println(session.delete("DELETE FROM user_s WHERE id_s = ? AND name = ?",list));
//        System.out.println(session.delete(user2));

//        System.out.println(session.update("UPDATE user_s SET name = ? WHERE id_s = ?",list));
        System.out.println(session.select("SELECT * FROM user_s WHERE id_s = ?",list));
//        System.out.println(session.selectList("SELECT * FROM user_s WHERE id_s = ?",list));
    }

}
