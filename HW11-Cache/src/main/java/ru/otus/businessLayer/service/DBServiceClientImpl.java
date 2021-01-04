package ru.otus.businessLayer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.businessLayer.model.Client;
import ru.otus.cache.HwCache;
import ru.otus.cache.MyCache;
import ru.otus.daoLayer.core.dao.ClientDao;

import java.util.Optional;

public class DBServiceClientImpl implements DBServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceClientImpl.class);

    private final ClientDao clientDao;

    private HwCache<Long, Client> cache = new MyCache<>();

    public DBServiceClientImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public long saveClient(Client client) {
        try (var sessionManager = clientDao.getSessionManager()) {
            sessionManager.beginSession();
            long clientId = 0;
            try {
                clientId = clientDao.insertOrUpdate(client);
                client.setId(clientId);
                cache.put(clientId, client);
                sessionManager.commitSession();
                logger.info("save {}, id: {}", client.getClass().getSimpleName(), clientId);
                return clientId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                cache.remove(clientId);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = cache.get(id);
        if (client != null) {
            logger.info("client: {}", client);
            return Optional.ofNullable(client);
        }
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
