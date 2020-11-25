package ru.otus.core.service;

import java.util.Optional;

public interface DBService<T> {

    int saveObject(T object);

    Optional<T> getObject(int id);
}
