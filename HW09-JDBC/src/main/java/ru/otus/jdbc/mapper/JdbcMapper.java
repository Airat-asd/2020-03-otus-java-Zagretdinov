package ru.otus.jdbc.mapper;

import ru.otus.core.sessionmanager.SessionManager;

public interface JdbcMapper<T> {
    void insert(T objectData);

    void update(T objectData);

    void insertOrUpdate(T objectData);

    T findById(int id, Class<T> clazz);

    int getId();

    SessionManager getSessionManager();
    }
