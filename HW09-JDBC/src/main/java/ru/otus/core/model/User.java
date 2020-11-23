package ru.otus.core.model;

import ru.otus.core.annotations.*;

/**
 * @author Ayrat Zagretdinov
 * created on 14.07.2020
 */
public class User {
    @Id
    private final int id;
    private final int age;
    private final String name;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "age=" + age +
                '}';
    }
}
