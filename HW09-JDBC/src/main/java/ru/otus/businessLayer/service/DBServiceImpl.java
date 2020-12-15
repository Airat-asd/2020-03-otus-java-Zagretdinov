package ru.otus.businessLayer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.daoLayer.core.dao.ObjectDao;

import java.util.Optional;

public class DBServiceImpl<T> implements DBService<T> {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    private final ObjectDao<T> objectDao;

    public DBServiceImpl(ObjectDao<T> objectDao) {
        this.objectDao = objectDao;
    }

    @Override
    public Object saveObject(T object) {
        return saveObject(object, false);
    }

    /**
     * Выполняется INSERT, если flagOfInsert = true и UPDATE, если flagOfInsert = false
     */
    @Override
    public Object saveObject(T object, boolean flagOfInsert) {
        try (var sessionManager = objectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var objectId = objectDao.insertObject(object, flagOfInsert);
                sessionManager.commitSession();
                logger.info("save {}, id: {}", object.getClass().getSimpleName(), objectId);
                return objectId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> getObject(Object id) {
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
