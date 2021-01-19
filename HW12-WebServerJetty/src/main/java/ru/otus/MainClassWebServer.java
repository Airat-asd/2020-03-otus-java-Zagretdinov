package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.AddressDataSet;
import ru.otus.businessLayer.model.PhoneDataSet;
import ru.otus.businessLayer.model.User;
import ru.otus.businessLayer.service.DBServiceUser;
import ru.otus.businessLayer.service.DBServiceUserImpl;
import ru.otus.daoLayer.core.dao.UserDao;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernateImplementation.HibernateUtils;
import ru.otus.hibernateImplementation.dao.UserDaoHibernate;
import ru.otus.hibernateImplementation.sessionmanager.SessionManagerHibernate;

import java.util.Collections;

public class MainClassWebServer {
    private static final Logger logger = LoggerFactory.getLogger(MainClassWebServer.class);
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";


    public static void main(String[] args) {

        DBServiceUser dbServiceUser = createDBServiceUser();
        dbServiceUser.saveUser(new User("Vasia", new AddressDataSet("Test_Street_1"), Collections.singletonList(new PhoneDataSet("+7894456"))));

    }

    public static DBServiceUser createDBServiceUser() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String dbUserName = configuration.getProperty("hibernate.connection.username");
        String dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class,
                AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);
        return dbServiceUser;
    }
}
