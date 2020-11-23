package ru.otus.jdbc.mapper;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private int id;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public <T> void insert(T objectData) {
            EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<T>((Class<T>) objectData.getClass(), getConnection());
            EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
            List<Object> params = new ArrayList<>();
//            Iterator iterator = entityClassMetaData.getFieldsWithoutId().iterator();
            entityClassMetaData.getFieldsWithoutId().forEach(field -> {
                field.setAccessible(true);
                try {
                    params.add(field.get(objectData));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            });

//            while (iterator.hasNext()) {
//                Field field = (Field) iterator.next();
//                field.setAccessible(true);
//                params.add(field.get(objectData));
//            }
            id = dbExecutor.executeInsert(getConnection(), entitySQLMetaData.getInsertSql(),
                    params);
    }

    @Override
    public void update(T objectData) {

    }

    @Override
    public void insertOrUpdate(T objectData) {

    }

    @Override
    public T findById(int id, Class<T> clazz) {
        AtomicReference<T> result = null;
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<T>(clazz, getConnection());
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        var asd = entitySQLMetaData.getSelectByIdSql();
        System.out.println("asd = " + asd);
        try {
            dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(),
                    id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                result.set(initializationObject(entityClassMetaData, rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
                            }
                        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
//                            logger.error(e.getMessage(), e);
                        }
                        return result.get();
                    });
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        return result.get();
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public int getId() {
        return id;
    }

    private T initializationObject(EntityClassMetaData<T> entityClassMetaData, int id, String name, int age) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//ленивая инициализация
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        T object = constructor.newInstance(id, name, age);
        return object;
    }
}
