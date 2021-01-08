package otus.daoLayer.mapper;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;
    private String getSelectByIdSql;
    private String getInsertSql;
    private String getInsertWithoutIdSql;
    private String getUpdateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: SELECT id, name, age FROM table WHERE id = ?
     */
    @Override
    public String getSelectByIdSql() {
        if (getSelectByIdSql == null) {
            getSelectByIdSql = String.format("SELECT %s, %s FROM %s WHERE %s = ?",
                    entityClassMetaData.getIdField().getName(), getStringWithoutId(), entityClassMetaData.getName(),
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
            String buffer2 = entityClassMetaData.getAllFields().stream()
                    .map(field -> "?")
                    .collect(Collectors.joining(", "));

            String buffer3 = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> field.getName() + " = EXCLUDED." + field.getName())
                    .collect(Collectors.joining(", "));

            getInsertSql = String.format("INSERT INTO %s (%s) VALUES (%s) ON CONFLICT (%s) DO UPDATE SET %s",
                    entityClassMetaData.getName(), getStringAllFields(), buffer2, entityClassMetaData.getIdField().getName(), buffer3);
        }
        return getInsertSql;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: INSERT INTO 'table' (name, age) VALUES (?, ?)
     */
    @Override
    public String getInsertWithoutIdSql() {
        if (getInsertWithoutIdSql == null) {
            getInsertWithoutIdSql = String.format("INSERT INTO %s (%s) VALUES (%s)", entityClassMetaData.getName(),
                    getStringWithoutId(), getStringQuestionMarkWithoutId());
        }
        return getInsertWithoutIdSql;
    }

    /**
     * Генерируется SQL запрос по следующему шаблону: UPDATE 'table' SET (name, age) VALUES (?, ?) WHERE id = ?
     */
    @Override
    public String getUpdateSql() {
        if (getUpdateSql == null) {
            getUpdateSql = String.format("UPDATE %s SET (%s) VALUES (%s) WHERE %s = ?", entityClassMetaData.getName(),
                    getStringWithoutId(), getStringQuestionMarkWithoutId(), entityClassMetaData.getIdField().getName());
        }
        return getUpdateSql;
    }

    @NotNull
    private String getStringWithoutId() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    @NotNull
    private String getStringAllFields() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    @NotNull
    private String getStringQuestionMarkWithoutId() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));
    }
}
