package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.*;
import ru.otus.base.TestContainersConfig;
import ru.otus.businessLayer.model.User;
import ru.otus.businessLayer.service.DBServiceClient;
import ru.otus.businessLayer.service.DBServiceClientImpl;
import ru.otus.daoLayer.core.dao.ClientDao;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernateImplementation.HibernateUtils;
import ru.otus.hibernateImplementation.dao.UserDaoHibernate;
import ru.otus.hibernateImplementation.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Демо работы с hibernate (с абстракциями) должно ")
class WithAbstractionsTest {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    protected static final String TEST_CLIENT_NAME = "Вася";
    protected static final String TEST_CLIENT_NEW_NAME = "НЕ Вася";
    protected SessionFactory sessionFactory;
    private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;
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

        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUserName);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        ClientDao clientDao = new UserDaoHibernate(sessionManager);
        dbServiceClient = new DBServiceClientImpl(clientDao);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    @DisplayName(" корректно сохранять клиента")
    void shouldCorrectSaveClient() {
        Client savedClient = buildDefaultClient();
        long id = dbServiceClient.saveClient(savedClient);
        Client loadedClient = loadClient(id);

        assertThat(loadedClient).isNotNull().usingRecursiveComparison().isEqualTo(savedClient);

        System.out.println(savedClient);
        System.out.println(loadedClient);
    }

    @Test
    @DisplayName(" корректно загружать клиента")
    void shouldLoadCorrectClient() {
        Client savedClient = buildDefaultClient();
        saveClient(savedClient);

        Optional<Client> mayBeClient = dbServiceClient.getClient(savedClient.getId());

        assertThat(mayBeClient).isPresent().get().usingRecursiveComparison().isEqualTo(savedClient);

        System.out.println(savedClient);
        mayBeClient.ifPresent(System.out::println);
    }

    @Test
    @DisplayName(" корректно изменять ранее сохраненного клиента")
    void shouldCorrectUpdateSavedClient() {
        Client savedClient = buildDefaultClient();
        saveClient(savedClient);

        Client savedClient2 = new Client(savedClient.getId(), TEST_CLIENT_NEW_NAME);
        long id = dbServiceClient.saveClient(savedClient2);
        Client loadedClient = loadClient(id);

        assertThat(loadedClient).isNotNull().usingRecursiveComparison().isEqualTo(savedClient2);

        System.out.println(savedClient);
        System.out.println(savedClient2);
        System.out.println(loadedClient);
    }


    protected Client buildDefaultClient() {
        return new Client(0, TEST_CLIENT_NAME);
    }

    protected void saveClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            saveClient(session, client);
        }
    }

    protected void saveClient(Session session, Client client) {
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
    }

    protected Client loadClient(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Client.class, id);
        }
    }

    protected EntityStatistics getUsageStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(Client.class.getName());
    }
}
