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

        DataSource dataSource = new DataSourcePostgres();
        sessionManager = new SessionManagerJdbc(dataSource);

        DbExecutor<Client> dbExecutor = new DbExecutorImpl<>();
        EntityClassMetaDataImpl<Client> clientEntityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(dbExecutor, sessionManager, clientEntityClassMetaData,
                new EntitySQLMetaDataImpl(clientEntityClassMetaData));
        ClientDao clientDao = new ClientDaoJdbc(sessionManager, jdbcMapperClient);
        dbServiceClient = new DBServiceClientImpl(clientDao);

    }

    @AfterEach
    void tearDown() {
        sessionManager.close();
    }

    @Test
    @DisplayName(" сохраняем клиента без кэша")
    void shouldCorrectSaveClient() {
        Client savedUser = buildDefaultClient(TEST_USER_NAME, TEST_AGE, TEST_FIELD);
        for (int i = 1; i < 10000; i++) {
            dbServiceClient.saveClient(savedUser);
        }

        for (int i = 1; i < 10000; i++) {
            Optional<Client> clientOptional = dbServiceClient.getClient(i);
        }
//        assertThat(loadedClient).isNotNull().usingRecursiveComparison().isEqualTo(savedUser);
    }


//    @Test
//    @DisplayName(" проверяем что при сохранении нового объекта не должно быть update")
//    void shouldNotUpdate() {
//        User savedUser1 = buildDefaultClient(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
//        User savedUser2 = buildDefaultClient(TEST_USER_NEW_NAME, TEST_ADDRESS_DATA_SET_NEW, TEST_PHONE_DATA_SET_NEW);
//        dbServiceUser.saveUser(savedUser1);
//        dbServiceUser.saveUser(savedUser2);
//
//        assertThat(getUsageStatistics(User.class).getUpdateCount()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName(" корректно загружать юзера")
//    void shouldLoadCorrectClient() {
//        User savedUser = buildDefaultClient(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
//        saveClient(savedUser);
//
//        Optional<User> mayBeUser = dbServiceUser.getUser(savedUser.getUserId());
//
//        assertThat(mayBeUser).isPresent().get().usingRecursiveComparison().isEqualTo(savedUser);
//
//        System.out.println(savedUser);
//        mayBeUser.ifPresent(System.out::println);
//    }
//
//    @Test
//    @DisplayName(" корректно изменять ранее сохраненного юзера")
//    void shouldCorrectUpdateSavedClient() {
//        User savedUser = buildDefaultClient(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
//        saveClient(savedUser);
//
//        User savedUser2 = new User(savedUser.getUserId(), TEST_USER_NEW_NAME, TEST_ADDRESS_DATA_SET_NEW,
//                Collections.singletonList(TEST_PHONE_DATA_SET_NEW));
//        TEST_ADDRESS_DATA_SET_NEW.setAddressId(savedUser.getUserId());
//        TEST_PHONE_DATA_SET_NEW.setUser(savedUser2);
//        TEST_PHONE_DATA_SET_NEW.setPhoneId(savedUser.getUserId());
//        System.out.println("savedUser=" + savedUser);
//
//        long id = dbServiceUser.saveUser(savedUser2);
//        System.out.println("savedUser2=" + savedUser2);
//        System.out.println("id=" + id);
//        User loadedUser = loadClient(id);
//
//        assertThat(loadedUser).isNotNull().usingRecursiveComparison().isEqualTo(savedUser2);
//
//        System.out.println(savedUser);
//        System.out.println(savedUser2);
//        System.out.println(loadedUser);
//        assertThat(getUsageStatistics(User.class).getInsertCount()).isEqualTo(1);
//        assertThat(getUsageStatistics(User.class).getUpdateCount()).isEqualTo(1);
//    }

    protected Client buildDefaultClient(String name, int age, String testField) {
        return new Client(name, age, testField);
    }

//    protected void saveClient(Client client) {
//        try (Session session = sessionFactory.openSession()) {
//            saveClient(session, client);
//        }
//    }
//
//    protected void saveClient(Session session, User user) {
//        session.beginTransaction();
//        session.save(user);
//        session.getTransaction().commit();
//    }
//
//    protected Client loadClient(long id) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.find(User.class, id);
//        }
//    }
//
}