package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.ObjectDao;
import ru.otus.core.model.*;
import ru.otus.core.service.*;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.ObjectDaoJdbc;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class MainClassJDBC {
    private static final Logger logger = LoggerFactory.getLogger(MainClassJDBC.class);

    public static void main(String[] args) throws Exception {
        DataSourceH2 dataSource = new DataSourceH2();
        MainClassJDBC mainClassJDBC = new MainClassJDBC();

        mainClassJDBC.createTable(dataSource, "create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutorImpl<>();
        JdbcMapper<User> jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor);
        ObjectDao<User> userDao = new ObjectDaoJdbc<>(jdbcMapper, User.class);

        DBService<User> dbServiceUser = new DBServiceImpl<>(userDao);
        int id = dbServiceUser.saveObject(new User(1, "dbServiceUser", 25));
        System.out.println(id);

        System.out.println("----------------------------------------------");

        Optional<User> user = dbServiceUser.getObject(id);
        System.out.println(user.orElse(new User(0,"0",0)));
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        System.out.println("-------------------------------------------");

//        mainClassJDBC.createTable(dataSource, "create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest int(3))");
//
//        DbExecutor<Account> dbExecutor1 = new DbExecutorImpl<>();
//        JdbcMapper<Account> jdbcMapper1 = new JdbcMapperImpl<>(sessionManager, dbExecutor1);
//        ObjectDao<Account> objectDao = new ObjectDaoJdbc<>(/*sessionManager, dbExecutor,*/ jdbcMapper1, Account.class);
//
//        DBService<Account> dbServiceUser1 = new DBServiceImpl<>(objectDao);
//        id = dbServiceUser1.saveObject(new Account(13, "Account", 35));
//        System.out.println(id);
//
//        System.out.println("--------------------------------------------------");
//        DataSourceH2 dataSource1 = new DataSourceH2();
//
//        mainClassJDBC.createTable(dataSource1, "create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)");
//
//        SessionManagerJdbc sessionManagerAccount = new SessionManagerJdbc(dataSource1);
//
//        DbExecutor<UserInterface> dbExecutorAccount = new DbExecutorImpl<>();
//        ObjectDao userDaoAccount = new ObjectDaoJdbc(sessionManagerAccount, dbExecutorAccount);
//
//        DBServiceUser dbServiceAccount = new DBServiceUserImpl(userDaoAccount);
//
//        id = dbServiceAccount.saveUser(new Account(10, "dbServiceAccount", 35));
//        Optional<UserInterface> account = dbServiceAccount.getUser(id);
//
//        account.ifPresentOrElse(
//                crAccount -> logger.info("created account, name:{}", crAccount.getName()),
//                () -> logger.info("user was not created")
//        );
    }

    private void createTable(DataSource dataSource, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
