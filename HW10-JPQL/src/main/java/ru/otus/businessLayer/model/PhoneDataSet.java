package ru.otus.businessLayer.model;

import javax.persistence.*;

@Entity
@Table(name = "phoneDataSet" )
public class PhoneDataSet {

    public PhoneDataSet() {}

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "number", nullable = false)
    private String number;

    public String getNumber() {
        return number;
    }

    public long getId() {
        return id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
