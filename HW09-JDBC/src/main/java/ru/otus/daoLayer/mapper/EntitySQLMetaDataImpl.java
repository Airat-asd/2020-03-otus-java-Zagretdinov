package ru.otus.daoLayer.mapper;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;
    private StringBuilder getSelectByIdSql;
    private StringBuilder getInsertSql;
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
            getSelectByIdSql = new StringBuilder("SELECT ");
            getSelectByIdSql.append(entityClassMetaData.getIdField().getName());
            entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .forEach(field -> {
                        getSelectByIdSql
                                .append(", ")
                                .append(field);
                    });
            getSelectByIdSql
                    .append(" FROM ")
                    .append(entityClassMetaData.getName())
                    .append(" WHERE ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
        }
        return getSelectByIdSql.toString();
    }

    /**
     * Генерируется SQL запрос по следующему шаблону:
     * INSERT INTO 'table' (id, name, age) VALUES (?, ?, ?) ON CONFLICT (id) DO UPDATE SET
     * name = excluded.name, age = excluded.age
     */
    @Override
    public String getInsertSql() {
        if (getInsertSql == null) {
            getInsertSql = new StringBuilder("INSERT INTO " + entityClassMetaData.getName() + "(" + entityClassMetaData.getIdField().getName());
            entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .forEach(field -> {
                        getInsertSql
                                .append(", ")
                                .append(field);
                    });
            getInsertSql
                    .append(") VALUES (?, ?, ?) ON CONFLICT (")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(") DO UPDATE SET ");
            entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName())
                    .forEach(field -> {
                        getInsertSql
                                .append(field)
                                .append(" = excluded.")
                                .append(field)
                                .append(", ");
                    });
            getInsertSql.delete(getInsertSql.length() - 2, getInsertSql.length());

        }
        return getInsertSql.toString();
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
            getInsertWithoutIdSql.append(") VALUES (?, ?)");

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
                    .append(") = (?, ?) WHERE ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
        }
        return getUpdateSql.toString();
    }
}
