package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static final Logger logger = LoggerFactory.getLogger(DbExecutorImpl.class);

    @Override
    public int executeInsert(Connection connection, String sql, List<Object> params) {
        Savepoint savePoint = null;
        try {
            savePoint = connection.setSavepoint("savePointName");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback(savePoint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            logger.error(ex.getMessage(), ex);
            try {
                throw ex;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public Optional<T> executeSelect(Connection connection, String sql, int id, Function<ResultSet, T> rsHandler) {
        try (var pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (var rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
