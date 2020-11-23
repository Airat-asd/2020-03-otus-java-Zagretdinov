package ru.otus.jdbc.mapper;

import java.sql.SQLException;

public interface JdbcMapper<T> {
    <T> void insert(T objectData) throws NoSuchMethodException, SQLException, IllegalAccessException;

    void update(T objectData);

    void insertOrUpdate(T objectData);

    T findById(int id, Class<T> clazz);

    int getId();
}
