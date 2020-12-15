package ru.otus.daoLayer.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;
import ru.otus.daoLayer.mapper.JdbcMapper;

import java.util.Optional;

public class ObjectDaoJdbc<T> implements ObjectDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDaoJdbc.class);
    private final JdbcMapper<T> jdbcMapper;
    private final Class<T> clazz;

    public ObjectDaoJdbc(JdbcMapper<T> jdbcMapper, Class<T> clazz) {
        this.jdbcMapper = jdbcMapper;
        this.clazz = clazz;
    }

    @Override
    public Object insertObject(T object, boolean flagOfInsert) {
        try {
            jdbcMapper.insert(object, flagOfInsert);
            return jdbcMapper.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public Optional<T> findById(Object id) {
        return Optional.ofNullable(jdbcMapper.findById(id, clazz));
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
