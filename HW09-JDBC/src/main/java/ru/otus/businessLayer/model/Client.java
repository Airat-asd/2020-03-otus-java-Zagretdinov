package ru.otus.businessLayer.model;

import ru.otus.daoLayer.core.annotations.Id;

/**
 * @author Ayrat Zagretdinov
 * created on 14.12.2020
 */
public class Client {
    @Id
    private final long id;
    private final String name;
    private final int age;
    private final String testField;


    public Client(long id, String name, int age, String testField) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.testField = testField;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", testField=" +testField +
                '}';
    }
}
