package ru.otus.daoLayer.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.daoLayer.core.sessionmanager.SessionManager;
import ru.otus.jdbcImplementation.DbExecutor;
import ru.otus.jdbcImplementation.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private EntityClassMetaData<T> entityClassMetaData;
    private EntitySQLMetaData entitySQLMetaData;
    private final List<Object> params = new ArrayList<>();
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private Object id;
    private T object;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void insert(T objectData, boolean flagOfInsert) throws IllegalAccessException {
        this.object = objectData;
        createParameters(objectData);
        var id = entityClassMetaData.getIdField().get(objectData);
        if (id == null || id.equals(0)) {
            params.remove(0);
            try {
                executor(entitySQLMetaData.getInsertWithoutIdSql(), params);
            } catch (SQLException troubles) {
                troubles.printStackTrace();
            }
        } else {
            if (flagOfInsert) {
                insertOrUpdate(objectData);
            } else {
                params.add(params.get(0));
                params.remove(0);
                try {
                    update(objectData);
                } catch (SQLException troubles) {
                    if (troubles.getErrorCode() == 0) {
                        logger.info("There is no '" + objectData.getClass().getSimpleName() +
                                "' object in the database, the object will be added to the database with a new ID.");
                        insertOrUpdate(objectData);
                    } else {
                        logger.info(troubles.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        try {
            params.clear();
            createParameters(objectData);
            executor(entitySQLMetaData.getInsertSql(), params);
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }

    @Override
    public void update(T objectData) throws SQLException {
        executor(entitySQLMetaData.getUpdateSql(), params);
    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        if (!this.object.getClass().equals(clazz)) {
            entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
            entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        }
        try {
            return dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(),
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

    private void createParameters(T objectData) {
        entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
        entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        entityClassMetaData.getIdField().setAccessible(true);

        try {
            params.add(entityClassMetaData.getIdField().get(objectData));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                params.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public Object getId() {
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

    private void executor(String insertSQL, List<Object> params) throws SQLException {
        id = dbExecutor.executeInsert(getConnection(), insertSQL, params);
    }
}