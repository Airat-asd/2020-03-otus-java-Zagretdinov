package ru.otus.businessLayer.model;

import ru.otus.daoLayer.core.annotations.Id;

/**
 * @author Ayrat Zagretdinov
 * created on 14.12.2020
 */
public class Client {
    @Id
    private long id;
    private String name;
    private int age;
    private String testField;

    public Client() {}

    public Client(long id, String name, int age, String testField) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.testField = testField;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
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
