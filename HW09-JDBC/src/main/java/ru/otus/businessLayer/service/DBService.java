package ru.otus.businessLayer.service;

import java.util.Optional;

public interface DBService<T> {

    Object saveObject(T object);

    Object saveObject(T object, boolean flagOfInsert);

    Optional<T> getObject(Object id);
}
