package ru.otus.businessLayer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tUsers" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressDataSet addressDataSets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
    private List<PhoneDataSet> phoneDataSet = new ArrayList<>();

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(long userId, String name, AddressDataSet addressDataSets, List<PhoneDataSet> phoneDataSet) {
        this.userId = userId;
        this.name = name;
        this.addressDataSets = addressDataSets;
        this.phoneDataSet = phoneDataSet;
    }

    public User(String name, AddressDataSet addressDataSets, List<PhoneDataSet> phoneDataSet) {
        this.name = name;
        this.addressDataSets = addressDataSets;
        this.phoneDataSet = phoneDataSet;
    }

    public long getUserId() {
        return userId;
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

    public List<PhoneDataSet> getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(List<PhoneDataSet> phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
    }

    public AddressDataSet getAddressDataSets() {
        return addressDataSets;
    }

    public void setAddressDataSets(AddressDataSet addressDataSet) {
        this.addressDataSets = addressDataSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneDataSet=" + phoneDataSet +
                ", addressDataSet=" + addressDataSets +
                '}';
    }
}
