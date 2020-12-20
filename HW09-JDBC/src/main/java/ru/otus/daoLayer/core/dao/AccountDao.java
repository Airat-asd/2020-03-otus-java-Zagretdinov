package ru.otus.daoLayer.core.dao;

import ru.otus.businessLayer.model.Account;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {

    String insert(Account account);

    String insertOrUpdate(Account account);

    Optional<Account> findById(String id);

    String getIdClient(Account account);

    SessionManager getSessionManager();
}
