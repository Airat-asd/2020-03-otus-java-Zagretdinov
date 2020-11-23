package ru.otus.core.model;

import ru.otus.core.annotations.*;

/**
 * @author Ayrat Zagretdinov
 * created on 14.07.2020
 */
public class Account {
    @Id
    private final int no;
    private final String type;
    private final int rest;

    public Account(int no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public int getId() {
        return no;
    }

    public String getName() {
        return type;
    }

    public int getAge() {
        return rest;
    }


    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                "rest=" + rest +
                '}';
    }
}
