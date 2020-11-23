package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.*;
import ru.otus.core.service.*;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
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
        UserDao<User> userDao = new UserDaoJdbc<>(sessionManager, dbExecutor, jdbcMapper, User.class);

        DBServiceUser<User> dbServiceUser = new DBServiceUserImpl<>(userDao);
        int id = dbServiceUser.saveUser(new User(10, "dbServiceUser", 25));
        System.out.println(id);
//        Optional<User> user = dbServiceUser.getUser(id);
//        System.out.println(user.orElse(new User(0,"0",0)));
//        user.ifPresentOrElse(
//                crUser -> logger.info("created user, name:{}", crUser.getName()),
//                () -> logger.info("user was not created")
//        );

//        System.out.println("--------------------------------------------------");
//        DataSourceH2 dataSource1 = new DataSourceH2();
//
//        mainClassJDBC.createTable(dataSource1, "create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)");
//
//        SessionManagerJdbc sessionManagerAccount = new SessionManagerJdbc(dataSource1);
//
//        DbExecutor<UserInterface> dbExecutorAccount = new DbExecutorImpl<>();
//        UserDao userDaoAccount = new UserDaoJdbc(sessionManagerAccount, dbExecutorAccount);
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
