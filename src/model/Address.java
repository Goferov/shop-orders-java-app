package model;

import java.io.Serializable;
import java.util.Objects;

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
        return "Ulica: " + street + "\n\n" +
                "Numer budynku: " + houseNumber + "\n\n" +
                "Numer mieszkania: " + (apartmentNumber.isEmpty() ? "N/A" : apartmentNumber) + "\n\n" +
                "Miasto: " + city + "\n\n" +
                "Kod pocztowy: " + postalCode + "\n\n" +
                "Województwo: " + state + "\n\n" +
                "Kraj: " + country + "\n\n";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(apartmentNumber, address.apartmentNumber) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(state, address.state) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, apartmentNumber, city, postalCode, state, country);
    }
}
