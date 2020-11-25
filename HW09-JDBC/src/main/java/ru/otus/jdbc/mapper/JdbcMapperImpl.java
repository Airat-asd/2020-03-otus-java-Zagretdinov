package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private EntityClassMetaData<T> entityClassMetaData;
    private EntitySQLMetaData entitySQLMetaData;
    private List<Object> params = new ArrayList<>();
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private int id;
    private T object;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void insert(T objectData) {
        object = objectData;
        entityClassMetaData = new EntityClassMetaDataImpl<T>((Class<T>) objectData.getClass(), getConnection());
        entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                params.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        id = dbExecutor.executeInsert(getConnection(), entitySQLMetaData.getInsertSql(),
                params);
    }

    @Override
    public T findById(int id, Class<T> clazz) {
        if (!object.getClass().equals(clazz)) {
            System.out.println("!!!not equals");
            entityClassMetaData = new EntityClassMetaDataImpl<T>(clazz, getConnection());
            entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        } else {
            System.out.println("!!!equals, object = " + clazz);
        }
        System.out.println("!!!entitySQLMetaData.getSelectByIdSql() = " + entitySQLMetaData.getSelectByIdSql());
        try {
            Optional<T> returnObject = dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                return initializationObject(entityClassMetaData, rs.getInt("id"),
                                        rs.getString("name"), rs.getInt("age"));
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
            return returnObject.orElse(initializationObject(entityClassMetaData, 0, "0", 0));

        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }


    @Override
    public void update(T objectData) {

    }

    @Override
    public void insertOrUpdate(T objectData) {

    }

    @Override
    public int getId() {
        return id;
    }

    private T initializationObject(EntityClassMetaData<T> entityClassMetaData, int id, String name, int age) {
//ленивая инициализация
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        T object = null;
        try {
            object = constructor.newInstance(id, name, age);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
