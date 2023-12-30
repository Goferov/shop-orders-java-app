package model;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String houseNumber;
    private String apartmentNumber; // optional
    private String city;
    private String postalCode;
    private String state;
    private String country;

    public Address(String street, String houseNumber, String apartmentNumber, String city, String postalCode, String state, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
    }
}
