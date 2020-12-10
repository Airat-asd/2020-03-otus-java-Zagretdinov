package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.ObjectDao;
import ru.otus.core.model.Client;
import ru.otus.core.service.DBService;
import ru.otus.core.service.DBServiceImpl;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.ObjectDaoJdbc;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.postgres.DataSourcePostgres;

import javax.sql.DataSource;

public class MainClassJDBC {
    private static final Logger logger = LoggerFactory.getLogger(MainClassJDBC.class);

    public static void main(String[] args) {
// Общая часть
        DataSource dataSource = new DataSourcePostgres();
        flywayMigrations(dataSource);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

//Работа спользователем
        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Client> jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor);
        ObjectDao<Client> clientDao = new ObjectDaoJdbc<>(jdbcMapper, Client.class);

        DBService<Client> dbServiceClient = new DBServiceImpl<>(clientDao);
        var id = dbServiceClient.saveObject(new Client(1, "dbServiceClient", 25));
        System.out.println(id);

        System.out.println("----------------------------------------------");
//
//        Optional<Client> client = dbServiceClient.getObject(id);
//        System.out.println(client.orElse(new Client(0, "0", 0)));
//        client.ifPresentOrElse(
//                crclient -> logger.info("created client, name:{}", crclient.getName()),
//                () -> logger.info("client was not created")
//        );
//
//        System.out.println("-------------------------------------------");
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
