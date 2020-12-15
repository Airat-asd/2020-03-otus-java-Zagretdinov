package ru.otus.daoLayer.mapper;

import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.sql.SQLException;

public interface JdbcMapper<T> {
    void insert(T objectData, boolean flagOfInsert) throws IllegalAccessException;

    void update(T objectData) throws SQLException;

    void insertOrUpdate(T objectData);

    T findById(Object id, Class<T> clazz);

    Object getId();

    SessionManager getSessionManager();
    }
