package ru.otus.daoLayer.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbcImplementation.DbExecutor;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private EntityClassMetaData<T> entityClassMetaData;
    private EntitySQLMetaData entitySQLMetaData;
    private final DbExecutor<T> dbExecutor;
    private final Map<T, List<Object>> objectParams = new HashMap<>();

    public JdbcMapperImpl(DbExecutor<T> dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Object> insert(T objectData, SessionManagerJdbc sessionManager) {
        List<Object> paramsInsert = createParametersForInsert(objectData);
        Object id = 0;
        try {
            id = executor(entitySQLMetaData.getInsertWithoutIdSql(), paramsInsert, sessionManager);
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
        return Optional.ofNullable(id);
    }

    @Override
    public Optional<Object> insertOrUpdate(T objectData, SessionManagerJdbc sessionManager) {
        List<Object> paramsInsertOrUpdate = createParameters(objectData);
        Object id = 0;
        if (paramsInsertOrUpdate.get(0).toString().equals("0") || paramsInsertOrUpdate.get(0) == null) {
            id = insert(objectData, sessionManager).orElse(0);
        } else {
            try {
                executor(entitySQLMetaData.getInsertSql(), paramsInsertOrUpdate, sessionManager);
                id = entityClassMetaData.getIdField().get(objectData);
            } catch (SQLException | IllegalAccessException troubles) {
                troubles.printStackTrace();
            }
        }
        return Optional.ofNullable(id);
    }

    @Override
    public void update(T objectData, SessionManagerJdbc sessionManager) throws SQLException {
        executor(entitySQLMetaData.getUpdateSql(), createParametersForUpdate(objectData), sessionManager);
    }

    @Override
    public T findById(Object id, Class<T> clazz, SessionManagerJdbc sessionManager) {
        if (this.entityClassMetaData == null) {
            entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
            entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        }
        try {
            return dbExecutor.executeSelect(sessionManager.getCurrentSession().getConnection(), entitySQLMetaData.getSelectByIdSql(),
                    id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                return initializationObject(entityClassMetaData,
                                        rs.getObject(entityClassMetaData.getIdField().getName()),
                                        rs.getObject(entityClassMetaData.getFieldsWithoutId().get(0).getName()),
                                        rs.getObject(entityClassMetaData.getFieldsWithoutId().get(1).getName()));
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
        if (this.entityClassMetaData == null) {
            this.entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
            this.entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
            this.entityClassMetaData.getIdField().setAccessible(true);
        }
        List<Object> readyParameters = objectParams.get(objectData);
        if (readyParameters == null) {
            try {
                parameters.add(entityClassMetaData.getIdField().get(objectData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.entityClassMetaData.getFieldsWithoutId().forEach(field -> {
                field.setAccessible(true);
                try {
                    parameters.add(field.get(objectData));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            this.objectParams.put(objectData, parameters);
        } else return readyParameters;

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

    private T initializationObject(EntityClassMetaData<T> entityClassMetaData, Object var1, Object var2, Object var3) {
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        T object = null;
        try {
            object = constructor.newInstance(var1, var2, var3);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    Object executor(String insertSQL, List<Object> params, SessionManagerJdbc sessionManager) throws SQLException {
        return dbExecutor.executeInsert(sessionManager.getCurrentSession().getConnection(), insertSQL, params);
    }
}
