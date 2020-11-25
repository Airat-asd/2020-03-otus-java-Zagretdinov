package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.ObjectDao;

import java.util.Optional;

public class DBServiceImpl<T> implements DBService<T> {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    private final ObjectDao<T> objectDao;

    public DBServiceImpl(ObjectDao<T> objectDao) {
        this.objectDao = objectDao;
    }

    @Override
    public int saveObject(T object) {
        try (var sessionManager = objectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var objectId = objectDao.insertObject(object);
                sessionManager.commitSession();

                logger.info("created user: {}", objectId);
                return objectId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> getObject(int id) {
        try (var sessionManager = objectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> objectOptional = objectDao.findById(id);
                logger.info("user: {}", objectOptional.orElse(null));
                return objectOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
