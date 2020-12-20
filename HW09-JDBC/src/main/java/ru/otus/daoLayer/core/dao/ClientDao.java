package ru.otus.daoLayer.core.dao;

import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface ClientDao {

    long insert(Client client);

    long insertOrUpdate(Client client);

    Optional<Client> findById(long id);

    long getIdClient(Client client);

    SessionManager getSessionManager();
}
