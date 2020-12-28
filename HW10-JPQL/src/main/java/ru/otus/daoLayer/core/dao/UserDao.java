package ru.otus.daoLayer.core.dao;

import ru.otus.businessLayer.model.User;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {

    long insert(User user);

    void update(User user);

    long insertOrUpdate(User user);

    Optional<User> findById(long id);

    SessionManager getSessionManager();
}
