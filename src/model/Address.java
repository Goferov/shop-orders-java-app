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

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
    @Override
    public String toString() {
        return "Ulica: " + street + '\n' +
                "Numer budynku: " + houseNumber + '\n' +
                "Numer mieszkania: " + (apartmentNumber.isEmpty() ? "N/A" : apartmentNumber) + '\n' +
                "Miasto: " + city + '\n' +
                "Kod pocztowy: " + postalCode + '\n' +
                "Województwo: " + state + '\n' +
                "Kraj: " + country + '\n';
    }
}
