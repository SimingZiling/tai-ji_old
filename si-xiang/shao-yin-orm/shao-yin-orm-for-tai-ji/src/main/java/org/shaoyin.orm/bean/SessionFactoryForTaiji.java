package org.shaoyin.orm.bean;

import org.shaoyin.orm.session.Session;
import org.shaoyin.orm.session.SessionFactory;
import org.shaoyin.orm.session.impl.SessionFactoryImpl;
import org.taiji.framework.beans.annotation.Bean;

import java.sql.SQLException;

@Bean
public class SessionFactoryForTaiji extends SessionFactoryImpl implements SessionFactory {

    @Override
    public Session openSession() {
        return super.openSession();
    }

    @Override
    public void close() throws SQLException {
        super.close();
    }

}
