package org.shaoyin.orm;

import org.shaoyin.orm.session.SqlSession;
import org.shaoyin.orm.session.SqlSessionFactory;
import org.shaoyin.orm.session.impl.DefaultSqlSessionFactory;

import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://132.232.19.205:3306/demo?autoReconnect=true&useSSL=false&serverTimezone=GMT%2b8&characterEncoding=utf8&allowMultiQueries=true",
                "root",
                "angel83528358");
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        try {
            System.out.println(sqlSession.selectList("SELECT * FROM `demo`.`example`"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
