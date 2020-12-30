package ru.otus.daoLayer.mapper;

import java.util.Optional;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;
    private String getSelectByIdSql;
    private String getInsertSql;
    private StringBuilder getInsertWithoutIdSql;
    private StringBuilder getUpdateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: SELECT id, name, age FROM table WHERE id = ?
     */
    @Override
    public String getSelectByIdSql() {
        if (getSelectByIdSql == null) {
            Optional<String> buffer = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .reduce((field1, field2) -> field1 + ", " + field2);
            getSelectByIdSql = String.format("SELECT %s, %s FROM %s WHERE %s = ?",
                    entityClassMetaData.getIdField().getName(), buffer.get(), entityClassMetaData.getName(),
                    entityClassMetaData.getIdField().getName());
        }
        return getSelectByIdSql;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону:
     * INSERT INTO 'table' (id, name, age) VALUES (?, ?, ?) ON CONFLICT (id) DO UPDATE SET
     * name = excluded.name, age = excluded.age
     */
    @Override
    public String getInsertSql() {
        if (getInsertSql == null) {
            Optional<String> buffer1 = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .reduce((field1, field2) -> field1 + ", " + field2);
            Optional<String> buffer2 = entityClassMetaData.getAllFields().stream()
                    .map(field -> "?")
                    .reduce((field1, field2) -> field1 + ", " + field2);
            Optional<String> buffer3 = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .reduce((field1, field2) -> field1 + " = excluded." + field1 + ", " + field2 + " = excluded." + field2);

            getInsertSql = String.format("INSERT INTO %s (%s, %s) VALUES (%s) ON CONFLICT (%s) DO UPDATE SET %s",
                    entityClassMetaData.getName(),entityClassMetaData.getIdField().getName(), buffer1.get(),
                    buffer2.get(), entityClassMetaData.getIdField().getName(), buffer3.get());
//            getInsertSql = new StringBuilder("INSERT INTO " + entityClassMetaData.getName() + "(" + entityClassMetaData.getIdField().getName());
//            entityClassMetaData.getFieldsWithoutId().stream()
//                    .map(field -> field.getName())
//                    .forEach(field -> {
//                        getInsertSql
//                                .append(", ")
//                                .append(field);
//                    });
//            getInsertSql.append(") VALUES (");
//            entityClassMetaData.getAllFields().stream()
//                    .forEach((field) -> getInsertSql.append("?,"));
//            getInsertSql.deleteCharAt(getInsertSql.length() - 1);
//            getInsertSql
//                    .append(") ON CONFLICT (")
//                    .append(entityClassMetaData.getIdField().getName())
//                    .append(") DO UPDATE SET ");
//            entityClassMetaData.getFieldsWithoutId().stream()
//                    .map(field -> field.getName())
//                    .forEach(field -> {
//                        getInsertSql
//                                .append(field)
//                                .append(" = excluded.")
//                                .append(field)
//                                .append(", ");
//                    });
//            getInsertSql.delete(getInsertSql.length() - 2, getInsertSql.length());
        }
        return getInsertSql;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: INSERT INTO 'table' (name, age) VALUES (?, ?)
     */
    @Override
    public String getInsertWithoutIdSql() {
        if (getInsertWithoutIdSql == null) {
            getInsertWithoutIdSql = new StringBuilder("INSERT INTO " + entityClassMetaData.getName() + " (");
            entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .forEach(field -> {
                        getInsertWithoutIdSql
                                .append(field)
                                .append(", ");
                    });
            getInsertWithoutIdSql.delete(getInsertWithoutIdSql.length() - 2, getInsertWithoutIdSql.length());
            getInsertWithoutIdSql.append(") VALUES (");
            entityClassMetaData.getFieldsWithoutId().stream()
                    .forEach((field) -> getInsertWithoutIdSql.append("?,"));
            getInsertWithoutIdSql.deleteCharAt(getInsertWithoutIdSql.length() - 1);
            getInsertWithoutIdSql.append(")");
        }
        return getInsertWithoutIdSql.toString();
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: UPDATE 'table' SET (name, age) VALUES (?, ?) WHERE id = ?
     */
    @Override
    public String getUpdateSql() {
        if (getUpdateSql == null) {
            getUpdateSql = new StringBuilder("UPDATE " + entityClassMetaData.getName() + " SET (");
            entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .forEach(field -> {
                        getUpdateSql
                                .append(field)
                                .append(", ");
                    });
            getUpdateSql.delete(getUpdateSql.length() - 2, getUpdateSql.length());
            getUpdateSql
                    .append(") = (");
            entityClassMetaData.getFieldsWithoutId().stream()
                    .forEach((field) -> getUpdateSql.append("?,"));
            getUpdateSql.deleteCharAt(getUpdateSql.length() - 1);
            getUpdateSql.
                    append(") WHERE ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
        }
        return getUpdateSql.toString();
    }
}
