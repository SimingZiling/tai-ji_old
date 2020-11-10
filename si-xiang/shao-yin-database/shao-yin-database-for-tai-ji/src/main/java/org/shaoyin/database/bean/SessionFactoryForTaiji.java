package org.shaoyin.database.bean;

import org.shaoyin.database.session.Session;
import org.shaoyin.database.session.impl.SessionFactoryImpl;
import org.taiji.framework.beans.annotation.Bean;

import java.sql.SQLException;

@Bean
public class SessionFactoryForTaiji extends SessionFactoryImpl implements org.shaoyin.database.session.SessionFactory {

    @Override
    public Session openSession() {
        return super.openSession();
    }

    @Override
    public void close() throws SQLException {
        super.close();
    }

}
