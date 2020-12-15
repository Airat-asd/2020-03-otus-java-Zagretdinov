package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Account;
import ru.otus.businessLayer.model.Client;
import ru.otus.businessLayer.service.DBService;
import ru.otus.businessLayer.service.DBServiceImpl;
import ru.otus.daoLayer.core.dao.ObjectDao;
import ru.otus.daoLayer.core.dao.ObjectDaoJdbc;
import ru.otus.daoLayer.mapper.JdbcMapper;
import ru.otus.daoLayer.mapper.JdbcMapperImpl;
import ru.otus.daoLayer.postgres.DataSourcePostgres;
import ru.otus.jdbcImplementation.DbExecutor;
import ru.otus.jdbcImplementation.DbExecutorImpl;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;

public class MainClassJDBC {
    private static final Logger logger = LoggerFactory.getLogger(MainClassJDBC.class);

    public static void main(String[] args) {
        Object id;
// Общая часть
        DataSource dataSource = new DataSourcePostgres();
        flywayMigrations(dataSource);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

//Работа спользователем
        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Client> jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor);
        ObjectDao<Client> clientDao = new ObjectDaoJdbc<>(jdbcMapper, Client.class);

        DBService<Client> dbServiceClient = new DBServiceImpl<>(clientDao);
        id = dbServiceClient.saveObject(new Client(14, "dbServiceClient14", 55), false);
        id = dbServiceClient.saveObject(new Client(24, "dbServiceClient24", 25), true);

        System.out.println("----------------------------------------------");

        Optional<Client> clientOptional = dbServiceClient.getObject(140);
        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );

        System.out.println("-------------------------------------------");
        DbExecutor<Account> dbExecutorAccount = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcMapperAccount = new JdbcMapperImpl<>(sessionManager, dbExecutorAccount);
        ObjectDao<Account> accountDao = new ObjectDaoJdbc<>(jdbcMapperAccount, Account.class);

        DBService<Account> dbServiceAccount = new DBServiceImpl<>(accountDao);
        id = dbServiceAccount.saveObject(new Account("11", "dbServiceAccount", 65.456), true);
        System.out.println("----------------------------------------------");

        Optional<Account> accountOptional = dbServiceAccount.getObject(id.toString());
        accountOptional.ifPresentOrElse(
                client -> logger.info("created account, type:{}", client.getName()),
                () -> logger.info("account was not created")
        );

        System.out.println("-------------------------------------------");


// Работа со счетом

    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}
