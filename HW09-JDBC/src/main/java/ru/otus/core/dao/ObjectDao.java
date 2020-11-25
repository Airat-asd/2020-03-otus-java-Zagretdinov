package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface ObjectDao<T> {

    int insertObject(T object);

    Optional<T> findById(int id);



    //void updateUser(User user);
    //void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
