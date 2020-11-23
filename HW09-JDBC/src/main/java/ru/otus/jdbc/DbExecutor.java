package ru.otus.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface  DbExecutor<T> {

    int executeInsert(Connection connection, String sql, List<Object> params);

    Optional<T> executeSelect(Connection connection, String sql, int id, Function<ResultSet, T> rsHandler);
}
