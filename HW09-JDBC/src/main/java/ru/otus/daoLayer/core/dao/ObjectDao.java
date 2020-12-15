package ru.otus.daoLayer.core.dao;

import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface ObjectDao<T> {

    Object insertObject(T object, boolean flagOfInsert);

    Optional<T> findById(Object id);

    SessionManager getSessionManager();
}
