package ru.otus.daoLayer.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Account;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;
import ru.otus.daoLayer.mapper.JdbcMapper;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);
    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<Account> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public String insert(Account account) {
        try {
            return jdbcMapper.insert(account, sessionManager).orElse(0).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public String insertOrUpdate(Account account) {
        try {
            return jdbcMapper.insertOrUpdate(account, sessionManager).orElse(0).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(jdbcMapper.findById(id, Account.class, sessionManager));
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}