package ru.otus.businessLayer.model;

import javax.persistence.*;

@Entity
@Table(name = "tAddressDataSet" )
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(name = "street", nullable = false)
    private String street;

    public AddressDataSet() {}

    public AddressDataSet(String street) {
        this.street = street;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                '}';
    }
}
