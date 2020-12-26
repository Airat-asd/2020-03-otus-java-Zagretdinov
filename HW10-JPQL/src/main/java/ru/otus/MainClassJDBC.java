package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClassJDBC {
    private static final Logger logger = LoggerFactory.getLogger(MainClassJDBC.class);

    public static void main(String[] args) {
//// Общая часть
//        DataSource dataSource = new DataSourcePostgres();
//        flywayMigrations(dataSource);
//        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
//
////Работа спользователем
//        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
//        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(dbExecutor);
//        ClientDao clientDao = new ClientDaoJdbc(sessionManager, jdbcMapperClient);

//        DBServiceClient dbServiceClient = new DBServiceClientImpl(clientDao);
//        long idClient = dbServiceClient.saveClient(new Client(14, "dbServiceClient14", 55));
//        System.out.println("----------------------------------------------");
//        Optional<Client> clientOptional = dbServiceClient.getClient(idClient);
//        clientOptional.ifPresentOrElse(
//                client -> logger.info("created client, name:{}", client.getName()),
//                () -> logger.info("client was not created")
//        );
//        System.out.println("----------------------------------------------");
//        idClient = dbServiceClient.saveClient(new Client(14, "dbServiceClient14New", 5));
//        System.out.println("----------------------------------------------");
//        clientOptional = dbServiceClient.getClient(idClient);
//        clientOptional.ifPresentOrElse(
//                client -> logger.info("created client, name:{}", client.getName()),
//                () -> logger.info("client was not created")
//        );
//        System.out.println("----------------------------------------------");
//        idClient = dbServiceClient.saveClient(new Client(0, "dbServiceClient0", 0));
//        System.out.println("----------------------------------------------");
//        clientOptional = dbServiceClient.getClient(idClient);
//        clientOptional.ifPresentOrElse(
//                client -> logger.info("created client, name:{}", client.getName()),
//                () -> logger.info("client was not created")
//        );
//
//
//        System.out.println("-------------------------------------------");
//    }
//
//    private static void flywayMigrations(DataSource dataSource) {
//        logger.info("db migration started...");
//        var flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .locations("classpath:/db/migration")
//                .load();
//        flyway.migrate();
//        logger.info("db migration finished.");
//        logger.info("***");
    }
}
