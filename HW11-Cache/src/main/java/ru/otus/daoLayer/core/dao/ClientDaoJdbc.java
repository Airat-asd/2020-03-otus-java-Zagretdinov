package ru.otus.daoLayer.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;
import ru.otus.daoLayer.mapper.JdbcMapper;
import ru.otus.base.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class ClientDaoJdbc implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoJdbc.class);
    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Client> jdbcMapper;

    public ClientDaoJdbc(SessionManagerJdbc sessionManager, JdbcMapper<Client> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public long insert(Client client) {
        try {
            return Long.parseLong(jdbcMapper.insert(client).orElse(0).toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(Client client) {
        try {
            return Long.parseLong(jdbcMapper.insertOrUpdate(client).orElse(0).toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(jdbcMapper.findById(id, Client.class));
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
