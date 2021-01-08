package ru.otus.businessLayer.model;

import javax.persistence.*;

@Entity
@Table(name = "tPhoneDataSet" )
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long phoneId;

    @Column(name = "phone_number", nullable = false)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public PhoneDataSet() {}

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet(String number, User user) {
        this.number = number;
        this.user = user;
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "phoneId=" + phoneId +
                ", phone_number='" + number + '\'' +
                '}';
    }
}
