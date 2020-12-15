package ru.otus.daoLayer.mapper;

import java.util.function.Supplier;

public class JdbcMapperException extends Exception implements Supplier<String> {

    @Override
    public String get() {
        return "Object not created";
    }
}