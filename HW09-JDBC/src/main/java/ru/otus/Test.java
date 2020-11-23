package ru.otus;

import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        DataSourceH2 dataSource = new DataSourceH2();
        Test mainClassJDBC = new Test();

        mainClassJDBC.createTable(dataSource, "create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");

    }

    private void createTable(DataSource dataSource, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        }
        System.out.println("table created");

        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet result = metaData.getColumns("TEST", "PUBLIC", "USER", null);
        List<String> tables = new ArrayList<>();
        while(result.next()) {
            tables.add(result.getString("COLUMN_NAME"));
        }
        System.out.println("tables = " + tables);


    }

}
