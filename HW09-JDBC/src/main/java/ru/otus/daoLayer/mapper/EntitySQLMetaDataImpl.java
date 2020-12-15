package ru.otus.daoLayer.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: SELECT id, name, age FROM table WHERE id = ?
     */
    @Override
    public String getSelectByIdSql() {
        StringBuilder buffer = new StringBuilder("SELECT ");
        buffer.append(entityClassMetaData.getIdField().getName());
        entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> ((Field) field).getName())
                .forEach(field -> buffer.append(", " + field));
        buffer.append(" FROM " + entityClassMetaData.getName() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?");
        return buffer.toString();
    }

    /**
     * Генерируется SQL запрос по следующему шаблону:
     * INSERT INTO 'table' (id, name, age) VALUES (?, ?, ?) ON CONFLICT (id) DO UPDATE SET
     * name = excluded.name, age = excluded.age
     */
    @Override
    public String getInsertSql() {
        StringBuilder buffer = new StringBuilder("INSERT INTO " + entityClassMetaData.getName() + "(" + entityClassMetaData.getIdField().getName());
        entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> ((Field) field).getName())
                .forEach(field -> buffer.append(", " + field));
        buffer.append(") VALUES (?, ?, ?) ON CONFLICT (" + entityClassMetaData.getIdField().getName() + ") DO UPDATE SET ");
        entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> ((Field) field).getName())
                .forEach(field -> buffer.append(field + " = excluded." + field + ", "));
        buffer.delete(buffer.length() - 2, buffer.length());
        return buffer.toString();
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: INSERT INTO 'table' (name, age) VALUES (?, ?)
     */
    @Override
    public String getInsertWithoutIdSql() {
        StringBuilder buffer = new StringBuilder("INSERT INTO " + entityClassMetaData.getName() + " (");
        entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> ((Field) field).getName())
                .forEach(field -> buffer.append(field + ", "));
        buffer.delete(buffer.length() - 2, buffer.length());
        buffer.append(") VALUES (?, ?)");
        return buffer.toString();
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: UPDATE 'table' SET (name, age) VALUES (?, ?) WHERE id = ?
     */
    @Override
    public String getUpdateSql() {
        StringBuilder buffer = new StringBuilder("UPDATE " + entityClassMetaData.getName() + " SET (");
        entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> ((Field) field).getName())
                .forEach(field -> buffer.append(field + ", "));
        buffer.delete(buffer.length() - 2, buffer.length());
        buffer.append(") = (?, ?) WHERE " + entityClassMetaData.getIdField().getName() + " = ?");
        return buffer.toString();
    }


}
