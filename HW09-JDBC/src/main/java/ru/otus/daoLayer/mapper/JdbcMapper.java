package ru.otus.daoLayer.mapper;

import java.sql.SQLException;
import java.util.Optional;

public interface JdbcMapper<T> {
    Optional<Object> insert(T objectData);

    void update(T objectData) throws SQLException;

    Optional<Object> insertOrUpdate(T objectData);

    T findById(Object id, Class<T> clazz);
    }
