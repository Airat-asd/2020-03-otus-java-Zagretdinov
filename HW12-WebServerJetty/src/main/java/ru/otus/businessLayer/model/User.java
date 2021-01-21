package ru.otus.businessLayer.model;

import javax.persistence.*;

@Entity
@Table(name = "tUsers" )
public class User {

    @Id
    private String name = "";

    @Column(name = "passwordHash")
    private int passwordHash;

    @Column(name = "isAnAdministrator")
    private boolean isAnAdministrator = false;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, int passwordHash, boolean isAnAdministrator) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.isAnAdministrator = isAnAdministrator;
    }

    public User(String name, int passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAnAdministrator() {
        return isAnAdministrator;
    }

    public void setAnAdministrator(boolean anAdministrator) {
        isAnAdministrator = anAdministrator;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passwordHash=" + passwordHash +
                ", administrator=" + isAnAdministrator +
                '}';
    }
}
