package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.ObjectDao;
import ru.otus.core.dao.ObjectDaoException;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class ObjectDaoJdbc<T> implements ObjectDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDaoJdbc.class);
    private final JdbcMapper<T> jdbcMapper;
    private final Class<?> clazz;



    public ObjectDaoJdbc(JdbcMapper<T> jdbcMapper, Class<T> clazz) {
        this.jdbcMapper = jdbcMapper;
        this.clazz = clazz;
    }

    @Override
    public int insertObject(T object) {
        try {
            jdbcMapper.insert(object);
            return jdbcMapper.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ObjectDaoException(e);
        }
    }

    @Override
    public Optional<T> findById(int id) {
        Optional<T> object = Optional.of(jdbcMapper.findById(id, (Class<T>) clazz));

        return object;
    }


    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }

//
//    private Connection getConnection() {
//        return sessionManager.getCurrentSession().getConnection();
//    }
}
