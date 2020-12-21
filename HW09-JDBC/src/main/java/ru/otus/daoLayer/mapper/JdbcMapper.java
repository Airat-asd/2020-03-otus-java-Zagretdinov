package ru.otus.daoLayer.mapper;

import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.sql.SQLException;
import java.util.Optional;

public interface JdbcMapper<T> {
    Optional<Object> insert(T objectData, SessionManagerJdbc sessionManager);

    void update(T objectData, SessionManagerJdbc sessionManager) throws SQLException;

    Optional<Object> insertOrUpdate(T objectData, SessionManagerJdbc sessionManager);

    T findById(Object id, Class<T> clazz, SessionManagerJdbc sessionManager);
    }
