package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Account;
import ru.otus.businessLayer.model.Client;
import ru.otus.businessLayer.service.DBServiceAccount;
import ru.otus.businessLayer.service.DBServiceAccountImpl;
import ru.otus.businessLayer.service.DBServiceClient;
import ru.otus.businessLayer.service.DBServiceClientImpl;
import ru.otus.daoLayer.core.dao.AccountDao;
import ru.otus.daoLayer.core.dao.AccountDaoJdbc;
import ru.otus.daoLayer.core.dao.ClientDao;
import ru.otus.daoLayer.core.dao.ClientDaoJdbc;
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
// Общая часть
        DataSource dataSource = new DataSourcePostgres();
        flywayMigrations(dataSource);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

//Работа спользователем
        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(dbExecutor, sessionManager, Client.class);
        ClientDao clientDao = new ClientDaoJdbc(sessionManager, jdbcMapperClient);

        DBServiceClient dbServiceClient = new DBServiceClientImpl(clientDao);
        long idClient = dbServiceClient.saveClient(new Client(14, "dbServiceClient14", 55, "test1"));
        System.out.println("----------------------------------------------");

        Optional<Client> clientOptional = dbServiceClient.getClient(idClient);
        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );
        System.out.println("----------------------------------------------");
        idClient = dbServiceClient.saveClient(new Client(14, "dbServiceClient14New", 5, "test3"));
        System.out.println("----------------------------------------------");
        clientOptional = dbServiceClient.getClient(idClient);
        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );
        System.out.println("----------------------------------------------");
        idClient = dbServiceClient.saveClient(new Client(0, "dbServiceClient0", 0, "test4"));
        System.out.println("----------------------------------------------");
        clientOptional = dbServiceClient.getClient(idClient);
        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );
        System.out.println("-------------------------------------------");
        DbExecutor<Account> dbExecutorAccount = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcMapperAccount = new JdbcMapperImpl<>(dbExecutorAccount, sessionManager, Account.class);
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, jdbcMapperAccount);

        DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);
        String idAccount = dbServiceAccount.saveAccount(new Account("11", "dbServiceAccount", 65.456));
        System.out.println("----------------------------------------------");

        Optional<Account> accountOptional = dbServiceAccount.getAccount(idAccount);
        accountOptional.ifPresentOrElse(
                account -> logger.info("created account, type:{}", account.getName()),
                () -> logger.info("account was not created")
        );

        System.out.println("-------------------------------------------");
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
