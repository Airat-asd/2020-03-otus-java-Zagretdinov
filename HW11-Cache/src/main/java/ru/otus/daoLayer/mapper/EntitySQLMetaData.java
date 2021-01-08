package ru.otus.daoLayer.mapper;

public interface EntitySQLMetaData {

    String getSelectByIdSql();

    String getInsertSql();

    String getInsertWithoutIdSql();

    String getUpdateSql();
}
