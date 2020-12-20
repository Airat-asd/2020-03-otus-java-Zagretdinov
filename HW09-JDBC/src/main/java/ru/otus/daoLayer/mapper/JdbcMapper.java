package ru.otus.daoLayer.mapper;

import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.sql.SQLException;

public interface JdbcMapper<T> {
    void insert(T objectData, SessionManagerJdbc sessionManager);

    void update(T objectData, SessionManagerJdbc sessionManager) throws SQLException;

    void insertOrUpdate(T objectData, SessionManagerJdbc sessionManager);

    T findById(Object id, Class<T> clazz, SessionManagerJdbc sessionManager);

    Object getId(T objectData);
    }
