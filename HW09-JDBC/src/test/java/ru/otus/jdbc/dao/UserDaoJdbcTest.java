package ru.otus.jdbc.dao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.core.model.User;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Zagretdinov Airat
 * @version 1.0 date 23.11.2020
 **/

class UserDaoJdbcTest {
    UserDaoJdbc<User> daoJdbc;
    SessionManagerJdbc sessionManager;
    DbExecutor<User> dbExecutor;
    DataSourceH2 dataSource;
    Class<User> clazz;
    User user;
    JdbcMapperImpl jdbcMapper;
    private final static int EXPECTED_ID= 1;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSourceH2.class);
        sessionManager = mock(SessionManagerJdbc.class);
        dbExecutor = mock(DbExecutor.class);
        user = mock(User.class);
        jdbcMapper = mock(JdbcMapperImpl.class);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        daoJdbc = new UserDaoJdbc(sessionManager, dbExecutor, jdbcMapper, clazz);
    }

    @Test
    void findById() {
    }

    @Test
    void insertUser() {
        Mockito.doNothing().when(jdbcMapper).insert(user);
        Mockito.when(jdbcMapper.getId()).thenReturn(EXPECTED_ID);

        int actualId = daoJdbc.insertUser(user);

//        Assertions.assertEquals(EXPECTED_ID, actualId);


    }

    @Test
    void getSessionManager() {
    }
}