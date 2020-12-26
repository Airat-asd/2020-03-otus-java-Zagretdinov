package ru.otus.jdbcImplementation.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.dao.ClientDao;
import ru.otus.daoLayer.core.dao.DaoException;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;
import ru.otus.daoLayer.mapper.JdbcMapper;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

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
            return Long.parseLong(jdbcMapper.insert(client, sessionManager).orElse(0).toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Client client) {

    }

    @Override
    public long insertOrUpdate(Client client) {
        try {
            return Long.parseLong(jdbcMapper.insertOrUpdate(client, sessionManager).orElse(0).toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(jdbcMapper.findById(id, Client.class, sessionManager));
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
