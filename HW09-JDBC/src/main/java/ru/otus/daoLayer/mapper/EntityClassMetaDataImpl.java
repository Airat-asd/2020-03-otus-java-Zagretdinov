package ru.otus.daoLayer.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.daoLayer.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final Class<T> clazz;
    private String name;
    private Field[] fields;
    private Field idField;
    private Constructor<T> constructor;
    private List<Field> getFieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
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
        if (getFieldsWithoutId == null) {
            getFieldsWithoutId = new ArrayList<>(getAllFields());
            getFieldsWithoutId.remove(getIdField());
        }
        return getFieldsWithoutId;
    }

    private void reflectionName() {
        name = clazz.getSimpleName();
    }

    private void reflectionFields() {
        fields = clazz.getDeclaredFields();
    }

    private void reflectionConstructor() {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            constructor = (Constructor<T>) constructors[0];
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
}
