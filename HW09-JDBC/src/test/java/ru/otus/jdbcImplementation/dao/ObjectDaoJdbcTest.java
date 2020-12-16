package ru.otus.jdbcImplementation.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.dao.ObjectDaoJdbc;
import ru.otus.daoLayer.mapper.JdbcMapperImpl;
import ru.otus.daoLayer.postgres.DataSourcePostgres;
import ru.otus.jdbcImplementation.DbExecutor;

import java.util.Optional;

import static org.mockito.Mockito.mock;

/**
 * @author Zagretdinov Airat
 * @version 1.0 date 15.12.2020
 **/

class ObjectDaoJdbcTest {
    ObjectDaoJdbc<Client> daoJdbc;
    DbExecutor<Client> dbExecutor;
    DataSourcePostgres dataSource;
    Client client;
    JdbcMapperImpl<Client> jdbcMapper;

    private final static Class<Client> clazz = Client.class;
    private final static boolean flagOfInsert = true;
    private final static Object EXPECTED_ID = 1;
    private final static Client EXPECTED_CLIENT = new Client(1, "test", 10);
    private final static Optional<Client> EXPECTED_OPTIONAL = Optional.of(EXPECTED_CLIENT);

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSourcePostgres.class);
        dbExecutor = mock(DbExecutor.class);
        client = mock(Client.class);
        jdbcMapper = mock(JdbcMapperImpl.class);
        daoJdbc = new ObjectDaoJdbc(jdbcMapper, clazz);
    }

    @Test
    void findById() {
        Mockito.doReturn(EXPECTED_CLIENT).when(jdbcMapper).findById(EXPECTED_ID,clazz);
        var actualFindByIdReturn = daoJdbc.findById(EXPECTED_ID);
        Assertions.assertEquals(EXPECTED_OPTIONAL, actualFindByIdReturn);
    }

    @Test
    void insertUser() {
        try {
            Mockito.doNothing().when(jdbcMapper).insert(client, flagOfInsert);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Mockito.when(jdbcMapper.getId()).thenReturn(EXPECTED_ID);
        var actualId = daoJdbc.insertObject(client, flagOfInsert);
        Assertions.assertEquals(EXPECTED_ID, actualId);
    }

}