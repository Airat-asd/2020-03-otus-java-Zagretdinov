package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private Class<T> clazz;
    private String name;
    private Field[] fields;
    private Field idField;
    private Constructor<T> constructor;
    private final Connection connection;
    private List<String> columns;

    public EntityClassMetaDataImpl(Class<T> clazz, Connection connection) {
        this.clazz = clazz;
        this.connection = connection;
    }

    @Override
    public String getName() {
        if (name == null) {
            reflectionName();
        }
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            reflectionConstructor();
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            reflectionIdField();
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (fields == null) {
            reflectionFields();
        }
        return new ArrayList<>(Arrays.asList(fields));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> getFieldsWithoutId = new ArrayList<>(getAllFields());
        getFieldsWithoutId.remove(getIdField());
        return getFieldsWithoutId;
    }

    private void reflectionName() {
        name = clazz.getSimpleName();
    }

    private void reflectionFields() {
        fields = clazz.getDeclaredFields();
//        columns = getColumns();
//        Field fieldBuffer;
//        for (int columnNumber = 0; columnNumber < columns.size(); columnNumber++) {
//            for (int fieldNumber = 0; fieldNumber < fields.length; fieldNumber++) {
//                if (fields[fieldNumber].getName().toUpperCase().equals(columns.get(columnNumber))) {
//                    fieldBuffer = fields[columnNumber];
//                    fields[columnNumber] = fields[fieldNumber];
//                    fields[fieldNumber] = fieldBuffer;
//                }
//            }
//        }

    }

    private void reflectionConstructor() {
        try {
            constructor =  clazz.getConstructor(int.class, String.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void reflectionIdField() {
        if (fields == null) {
            reflectionFields();
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }
        if (idField == null) {
            logger.error("Annotation \"Id\" not found in the class: {}", name);
            throw new EntityClassMetaDataException("Annotation \"Id\" not found!");
        }
    }

    private List<String> getColumns() {
        DatabaseMetaData metaData = null;
        List<String> tables = null;
        try {
            metaData = connection.getMetaData();
            ResultSet result = metaData.getColumns("TEST", "PUBLIC", "USER", null);
            tables = new ArrayList<>();
            while (result.next()) {
                tables.add(result.getString("COLUMN_NAME"));
            }
            return tables;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tables;
    }
}
