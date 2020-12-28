package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.*;
import ru.otus.base.TestContainersConfig;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверяем наши entity ")
class TestingTheCreationOfTablesInHibernet {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    protected static final String TEST_USER_NAME = "Evgeni";
    protected static final String TEST_USER_NEW_NAME = "Alex";
    protected static PhoneDataSet TEST_PHONE_DATA_SET;
    protected static PhoneDataSet TEST_PHONE_DATA_SET_NEW;
    protected static AddressDataSet TEST_ADDRESS_DATA_SET;
    protected static AddressDataSet TEST_ADDRESS_DATA_SET_NEW;

    protected SessionFactory sessionFactory;
    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;
    private DBServiceUser dbServiceUser;

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
        TEST_PHONE_DATA_SET = new PhoneDataSet("+7894456");
        TEST_PHONE_DATA_SET_NEW = new PhoneDataSet("+12345");
        TEST_ADDRESS_DATA_SET = new AddressDataSet("Test_Street_1");
        TEST_ADDRESS_DATA_SET_NEW = new AddressDataSet("Test_Street_2");

        String dbUrl = System.getProperty("app.datasource.demo-db.jdbcUrl");
        String dbUserName = System.getProperty("app.datasource.demo-db.username");
        String dbPassword = System.getProperty("app.datasource.demo-db.password");

        var migrationsExecutor = new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword);
        migrationsExecutor.cleanDb();
        migrationsExecutor.executeMigrations();

        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUserName);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DBServiceUserImpl(userDao);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    @DisplayName(" корректно сохранять клиента, проверяем что создалось 3 таблицы")
    void shouldCorrectSaveClient() {
        User savedUser = buildDefaultUser(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
        System.out.println("savedUser = " + savedUser);

        long id = dbServiceUser.saveUser(savedUser);
        User loadedClient = loadUser(id);

        System.out.println("loadedClient = " + loadedClient);

        assertThat(loadedClient).isNotNull().usingRecursiveComparison().isEqualTo(savedUser);
    }


    @Test
    @DisplayName(" проверяем что при сохранении нового объекта не должно быть update")
    void shouldNotUpdate() {
        User savedUser1 = buildDefaultUser(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
        User savedUser2 = buildDefaultUser(TEST_USER_NEW_NAME, TEST_ADDRESS_DATA_SET_NEW, TEST_PHONE_DATA_SET_NEW);
        dbServiceUser.saveUser(savedUser1);
        dbServiceUser.saveUser(savedUser2);

        assertThat(getUsageStatistics(User.class).getUpdateCount()).isEqualTo(0);
    }
    @Test
    @DisplayName(" корректно загружать юзера")
    void shouldLoadCorrectClient() {
        User savedUser = buildDefaultUser(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
        saveUser(savedUser);

        Optional<User> mayBeUser = dbServiceUser.getUser(savedUser.getUserId());

        assertThat(mayBeUser).isPresent().get().usingRecursiveComparison().isEqualTo(savedUser);

        System.out.println(savedUser);
        mayBeUser.ifPresent(System.out::println);
    }

    @Test
    @DisplayName(" корректно изменять ранее сохраненного юзера")
    void shouldCorrectUpdateSavedClient() {
        User savedUser = buildDefaultUser(TEST_USER_NAME, TEST_ADDRESS_DATA_SET, TEST_PHONE_DATA_SET);
        saveUser(savedUser);

        User savedUser2 = new User(savedUser.getUserId(), TEST_USER_NEW_NAME, TEST_ADDRESS_DATA_SET_NEW,
                Collections.singletonList(TEST_PHONE_DATA_SET_NEW));
        TEST_ADDRESS_DATA_SET_NEW.setAddressId(savedUser.getUserId());
        TEST_PHONE_DATA_SET_NEW.setUser(savedUser2);
        TEST_PHONE_DATA_SET_NEW.setPhoneId(savedUser.getUserId());
        System.out.println("savedUser="+savedUser);

        long id = dbServiceUser.saveUser(savedUser2);
        System.out.println("savedUser2="+savedUser2);
        System.out.println("id="+id);
        User loadedUser = loadUser(id);

        assertThat(loadedUser).isNotNull().usingRecursiveComparison().isEqualTo(savedUser2);

        System.out.println(savedUser);
        System.out.println(savedUser2);
        System.out.println(loadedUser);
        assertThat(getUsageStatistics(User.class).getInsertCount()).isEqualTo(1);
        assertThat(getUsageStatistics(User.class).getUpdateCount()).isEqualTo(1);
    }

    protected User buildDefaultUser(String name, AddressDataSet addressDataSet, PhoneDataSet phoneDataSet) {
        User user = new User(name, addressDataSet, Collections.singletonList(phoneDataSet));
        phoneDataSet.setUser(user);
        return user;
    }

    protected void saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            saveUser(session, user);
        }
    }

    protected void saveUser(Session session, User user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    protected User loadUser(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }

    protected EntityStatistics getUsageStatistics(Class<?> clazz) {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(clazz.getName());
    }
}
