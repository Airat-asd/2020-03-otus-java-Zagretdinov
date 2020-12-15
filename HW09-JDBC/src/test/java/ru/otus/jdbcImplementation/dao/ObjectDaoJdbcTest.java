package ru.otus.jdbcImplementation.dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.dao.ObjectDaoJdbc;
import ru.otus.jdbcImplementation.DbExecutor;
import ru.otus.daoLayer.mapper.JdbcMapperImpl;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;
import ru.otus.daoLayer.postgres.DataSourcePostgres;

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