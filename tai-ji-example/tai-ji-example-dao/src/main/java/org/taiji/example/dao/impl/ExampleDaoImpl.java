package org.taiji.example.dao.impl;

import org.shaoyin.database.exception.DatabaseCoreException;
import org.shaoyin.database.exception.DoNotCreateException;
import org.shaoyin.database.session.Session;
import org.shaoyin.database.session.SessionFactory;
import org.shaoyin.database.sql.PackagSQL;
import org.taiji.example.dao.ExampleDao;
import org.taiji.example.model.Example;
import org.taiji.framework.beans.annotation.Dao;
import org.taiji.framework.beans.annotation.Inject;
import org.yang.localtools.exception.LocalToolsException;

import java.sql.SQLException;

@Dao
public class ExampleDaoImpl implements ExampleDao {

    @Inject
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.openSession();
    }


    @Override
    public void save(Example example) {
        try {
            getSession().insert(example);
        } catch (LocalToolsException | DatabaseCoreException | SQLException e) {
            e.printStackTrace();
        }
    }
}
