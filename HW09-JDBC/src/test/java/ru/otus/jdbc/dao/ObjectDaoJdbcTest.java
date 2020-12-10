package ru.otus.jdbc.dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.core.model.Client;
import ru.otus.core.model.User;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.postgres.DataSourcePostgres;

import static org.mockito.Mockito.mock;

/**
 * @author Zagretdinov Airat
 * @version 1.0 date 23.11.2020
 **/

class ObjectDaoJdbcTest {
    ObjectDaoJdbc<Client> daoJdbc;
    SessionManagerJdbc sessionManager;
    DbExecutor<Client> dbExecutor;
    DataSourcePostgres dataSource;
    Class<Client> clazz;
    Client client;
    JdbcMapperImpl jdbcMapper;
    private final static int EXPECTED_ID= 1;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSourcePostgres.class);
        sessionManager = mock(SessionManagerJdbc.class);
        dbExecutor = mock(DbExecutor.class);
        client = mock(Client.class);
        jdbcMapper = mock(JdbcMapperImpl.class);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        daoJdbc = new ObjectDaoJdbc(jdbcMapper, clazz);
    }

    @Test
    void findById() {
    }

    @Test
    void insertUser() {
        Mockito.doNothing().when(jdbcMapper).insert(client);
        Mockito.when(jdbcMapper.getId()).thenReturn(EXPECTED_ID);

        int actualId = daoJdbc.insertObject(client);

//        Assertions.assertEquals(EXPECTED_ID, actualId);


    }

    @Test
    void getSessionManager() {
    }
}