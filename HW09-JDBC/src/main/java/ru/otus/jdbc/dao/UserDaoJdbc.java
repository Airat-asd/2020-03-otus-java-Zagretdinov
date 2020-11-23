package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoJdbc<T> implements UserDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final JdbcMapper<T> jdbcMapper;
    private final Class<?> clazz;



    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor, JdbcMapper<T> jdbcMapper, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.jdbcMapper = jdbcMapper;
        this.clazz = clazz;

    }

    @Override
    public Optional<T> findById(int id) {
        try {
            T byId = jdbcMapper.findById(id, (Class<T>) clazz);
            return dbExecutor.executeSelect(getConnection(), "select * from user where id  = ?",
                    id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                return (T) new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public <T> int insertUser(T user) {
        try {
            jdbcMapper.insert(user);
            return jdbcMapper.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
