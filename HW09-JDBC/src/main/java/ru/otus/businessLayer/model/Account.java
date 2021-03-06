package ru.otus.businessLayer.model;

import ru.otus.daoLayer.core.annotations.Id;

/**
 * @author Ayrat Zagretdinov
 * created on 14.07.2020
 */

public class Account {
    @Id
    private String no;
    private String type;
    private double rest;

    public Account() {}

    public Account(String no, String type, double rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
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
