package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface ObjectDao<T> {

    int insertObject(T object);

    Optional<T> findById(int id);

    //void updateUser(Client object);
    //void insertOrUpdate(Client object);

    SessionManager getSessionManager();
}
