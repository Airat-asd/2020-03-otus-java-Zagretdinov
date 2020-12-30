package ru.otus.daoLayer.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbcImplementation.DbExecutor;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData entitySQLMetaData;
    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManager,
                          EntityClassMetaData entityClassMetaData, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<Object> insert(T objectData) {
        List<Object> paramsInsert = createParametersForInsert(objectData);
        Object id = 0;
        try {
            id = execute(entitySQLMetaData.getInsertWithoutIdSql(), paramsInsert);
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
        return Optional.ofNullable(id);
    }

    @Override
    public Optional<Object> insertOrUpdate(T objectData) {
        List<Object> paramsInsertOrUpdate = createParameters(objectData);
        Object id = 0;
        if (paramsInsertOrUpdate.get(0).toString().equals("0") || paramsInsertOrUpdate.get(0) == null) {
            id = insert(objectData).orElse(0);
        } else {
            try {
                execute(entitySQLMetaData.getInsertSql(), paramsInsertOrUpdate);
                id = entityClassMetaData.getIdField().get(objectData);
            } catch (SQLException | IllegalAccessException troubles) {
                troubles.printStackTrace();
            }
        }
        return Optional.ofNullable(id);
    }

    @Override
    public void update(T objectData) throws SQLException {
        List<Object> parametersForUpdate = createParametersForUpdate(objectData);
        execute(entitySQLMetaData.getUpdateSql(), parametersForUpdate);
    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        try {
            return dbExecutor.executeSelect(sessionManager.getCurrentSession().getConnection(), entitySQLMetaData.getSelectByIdSql(),
                    id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                return initializeObject(rs);
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            ).orElse(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private List<Object> createParameters(T objectData) {
        List<Object> parameters = new ArrayList<>();
        entityClassMetaData.getIdField().setAccessible(true);
        try {
            parameters.add(entityClassMetaData.getIdField().get(objectData));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                parameters.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return parameters;
    }

    private List<Object> createParametersForInsert(T objectData) {
        List<Object> paramsForInsert = createParameters(objectData);
        paramsForInsert.remove(0);
        return paramsForInsert;
    }

    private List<Object> createParametersForUpdate(T objectData) {
        List<Object> paramsUpdate = createParameters(objectData);
        paramsUpdate.add(paramsUpdate.get(0));
        paramsUpdate.remove(0);
        return paramsUpdate;
    }

    private T initializeObject(ResultSet rs) {
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        try {
            T entity = constructor.newInstance();
            entityClassMetaData.getAllFields()
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(field.getName()));

                        } catch (SQLException | IllegalAccessException throwables) {
                            throwables.printStackTrace();
                        }
                    });
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    Object execute(String insertSQL, List<Object> params) throws SQLException {
        return dbExecutor.executeInsert(sessionManager.getCurrentSession().getConnection(), insertSQL, params);
    }
}
