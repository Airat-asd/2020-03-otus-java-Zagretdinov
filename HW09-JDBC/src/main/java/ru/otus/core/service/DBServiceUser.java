package ru.otus.core.service;

import java.util.Optional;

public interface DBServiceUser<T> {

    <T> int saveUser(T user);

    Optional<T> getUser(int id);
}
