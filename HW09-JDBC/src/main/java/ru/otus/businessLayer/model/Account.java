package ru.otus.businessLayer.model;

import ru.otus.daoLayer.core.annotations.Id;

/**
 * @author Ayrat Zagretdinov
 * created on 14.07.2020
 */

public class Account {
    @Id
    private final String no;
    private final String type;
    private final double rest;

    public Account(String no, String type, double rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public String getId() {
        return no;
    }

    public String getName() {
        return type;
    }

    public double getAge() {
        return rest;
    }


    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
