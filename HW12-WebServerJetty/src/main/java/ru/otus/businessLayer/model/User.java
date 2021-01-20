package ru.otus.businessLayer.model;

import javax.persistence.*;

@Entity
@Table(name = "tUsers" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "passwordHash")
    private int passwordHash;

    @Column(name = "administratorFlag")
    private boolean administratorFlag = false;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, int passwordHash, boolean administratorFlag) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.administratorFlag = administratorFlag;
    }

    public User(long userId, String name, int passwordHash) {
        this.userId = userId;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public User(String name, int passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public long getUserId() {
        return userId;
    }

    public void setId(long userId) {
        this.userId = userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", passwordHash=" + passwordHash +
                ", administrator=" + administratorFlag +
                '}';
    }
}
