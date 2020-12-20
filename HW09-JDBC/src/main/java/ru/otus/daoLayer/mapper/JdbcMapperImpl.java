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
    private Object id;
    private final Map<T, List> objectParams = new HashMap<>();

    public JdbcMapperImpl(DbExecutor<T> dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void insert(T objectData, SessionManagerJdbc sessionManager) {
        List<Object> paramsInsert = createParametersForInsert(objectData);
        try {
            executor(entitySQLMetaData.getInsertWithoutIdSql(), paramsInsert, sessionManager);
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }

    @Override
    public void insertOrUpdate(T objectData, SessionManagerJdbc sessionManager) {
        List<Object> paramsInsertOrUpdate = createParameters(objectData);
        try {
            executor(entitySQLMetaData.getInsertSql(), paramsInsertOrUpdate, sessionManager);
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }

    @Override
    public void update(T objectData, SessionManagerJdbc sessionManager) throws SQLException {
        List<Object> paramsUpdate = createParameters(objectData);
        paramsUpdate.add(paramsUpdate.get(0));
        paramsUpdate.remove(0);
        executor(entitySQLMetaData.getUpdateSql(), paramsUpdate, sessionManager);
    }

    @Override
    public T findById(Object id, Class<T> clazz, SessionManagerJdbc sessionManager) {
        entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
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
        if (objectParams.get(objectData) == null) {
            entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
            entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
            entityClassMetaData.getIdField().setAccessible(true);
            try {
                this.id = entityClassMetaData.getIdField().get(objectData);
                parameters.add(id);
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
            objectParams.put(objectData, parameters);
        }
        return objectParams.get(objectData);
    }

    private List<Object> createParametersForInsert(T objectData) {
        List<Object> paramsForInsert = new ArrayList<>(createParameters(objectData));
        paramsForInsert.remove(0);
        return paramsForInsert;
    }

    @Override
    public Object getId(T objectData) {
        createParameters(objectData);
        return id;
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

    void executor(String insertSQL, List<Object> params, SessionManagerJdbc sessionManager) throws SQLException {
        id = dbExecutor.executeInsert(sessionManager.getCurrentSession().getConnection(), insertSQL, params);
    }
}
