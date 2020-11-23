package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Iterator;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return null;
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder buffer = new StringBuilder("select ");
        buffer.append(entityClassMetaData.getIdField().getName());
        Iterator<Field> iterator = entityClassMetaData.getFieldsWithoutId().iterator();
        while (iterator.hasNext()) {
            buffer.append(", ");
            Field fieldWithoutId = iterator.next();
            buffer.append(fieldWithoutId.getName());
        }
        buffer.append(" from " + entityClassMetaData.getName() + " where id = ?");
        return buffer.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder buffer = new StringBuilder("insert into " + entityClassMetaData.getName() + "(");
        Iterator iterator = entityClassMetaData.getFieldsWithoutId().iterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            buffer.append(field.getName());
            if (iterator.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(") values (?, ?)");
        return buffer.toString();
    }

    @Override
    public String getUpdateSql() {
        return null;
    }
}
