package ru.otus.businessLayer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Client;
import ru.otus.daoLayer.core.dao.ClientDao;

import java.util.Optional;

public class DBServiceClientImpl implements DBServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceClientImpl.class);

    private final ClientDao clientDao;

    public DBServiceClientImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Выполняется INSERT, если id = null or "0" и INSERT_OR_UPDATE, если id != "0" or null
     */
    @Override
    public long saveClient(Client client) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long clientId;
                if (clientDao.getIdClient(client) == 0) {
                    clientId = clientDao.insert(client);
                } else {
                    clientId = clientDao.insertOrUpdate(client);
                }
                sessionManager.commitSession();
                logger.info("save {}, id: {}", client.getClass().getSimpleName(), clientId);
                return clientId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Client> getClient(long id) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> clientOptional = clientDao.findById(id);
                logger.info("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
