package ru.otus;

/**
 * @author Zagretdinov Airat
 * @version 1.0 date 04.01.2021
 **/

import org.junit.jupiter.api.*;
import ru.otus.base.DbExecutor;
import ru.otus.base.DbExecutorImpl;
import ru.otus.base.TestContainersConfig;
import ru.otus.base.sessionmanager.SessionManagerJdbc;
import ru.otus.businessLayer.model.Client;
import ru.otus.businessLayer.service.DBServiceClient;
import ru.otus.businessLayer.service.DBServiceClientImpl;
import ru.otus.businessLayer.service.DBServiceClientImplWithoutCache;
import ru.otus.daoLayer.core.dao.ClientDao;
import ru.otus.daoLayer.core.dao.ClientDaoJdbc;
import ru.otus.daoLayer.mapper.EntityClassMetaDataImpl;
import ru.otus.daoLayer.mapper.EntitySQLMetaDataImpl;
import ru.otus.daoLayer.mapper.JdbcMapper;
import ru.otus.daoLayer.mapper.JdbcMapperImpl;
import ru.otus.daoLayer.postgres.DataSourcePostgres;
import ru.otus.flyway.MigrationsExecutorFlyway;

import javax.sql.DataSource;
import java.util.Optional;

@DisplayName("Проверяем наши entity ")
class TestCacheImplementation {
    protected static final String TEST_USER_NAME = "Evgeniy";
    protected static final String TEST_FIELD = "test_field";
    protected static final int TEST_AGE = 30;

    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;

    protected SessionManagerJdbc sessionManager;
    private DBServiceClient dbServiceClient;
    private DBServiceClientImplWithoutCache dbServiceClientImplWithoutCache;

    @BeforeAll
    public static void init() {
        CONTAINER = TestContainersConfig.CustomPostgreSQLContainer.getInstance();
        CONTAINER.start();
    }

    @AfterAll
    public static void shutdown() {
        CONTAINER.stop();
    }

    @BeforeEach
    public void setUp() {

        String dbUrl = System.getProperty("app.datasource.demo-db.jdbcUrl");
        String dbUserName = System.getProperty("app.datasource.demo-db.username");
        String dbPassword = System.getProperty("app.datasource.demo-db.password");

        var migrationsExecutor = new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword);
        migrationsExecutor.cleanDb();
        migrationsExecutor.executeMigrations();

        DataSource dataSource = new DataSourcePostgres(dbUrl, dbUserName, dbPassword);
        sessionManager = new SessionManagerJdbc(dataSource);

        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
        EntityClassMetaDataImpl<Client> clientEntityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(dbExecutor, sessionManager, clientEntityClassMetaData,
                new EntitySQLMetaDataImpl(clientEntityClassMetaData));
        ClientDao clientDao = new ClientDaoJdbc(sessionManager, jdbcMapperClient);
        dbServiceClient = new DBServiceClientImpl(clientDao);
        dbServiceClientImplWithoutCache = new DBServiceClientImplWithoutCache(clientDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName(" загружаем клиента с кэшем")
    void saveClientWithCache() {
        for (int i = 1; i < 100; i++) {
            Client savedUser = buildDefaultClient(i, TEST_USER_NAME, TEST_AGE, TEST_FIELD);
            dbServiceClient.saveClient(savedUser);
        }

        for (int i = 1; i < 100; i++) {
            Optional<Client> clientOptional = dbServiceClient.getClient(i);
        }
    }

    @Test
    @DisplayName(" загружаем клиента без кэша")
    void shouldCorrectSaveClient() {
        for (int i = 1; i < 100; i++) {
            Client savedUser = buildDefaultClient(i, TEST_USER_NAME, TEST_AGE, TEST_FIELD);
            dbServiceClientImplWithoutCache.saveClient(savedUser);
        }

        System.gc();
        for (int i = 1; i < 100; i++) {
            Optional<Client> clientOptional = dbServiceClientImplWithoutCache.getClient(i);
        }
    }

    protected Client buildDefaultClient(String name, int age, String testField) {
        return new Client(name, age, testField);
    }

    protected Client buildDefaultClient(int id, String name, int age, String testField) {
        return new Client(id, name, age, testField);
    }
}