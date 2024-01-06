package model;


import java.util.Objects;

public class Customer extends AbstractModel {
    private String name;
    private String lastname;
    private String company; // optional
    private String nip; // optional
    private Address address;
    private Address deliveryAddress; // optional

    public Customer(Integer id, String name, String lastname, String company, String nip, Address address, Address deliveryAddress) {
        super(id);
        this.name = name;
        this.lastname = lastname;
        this.company = company;
        this.nip = nip;
        this.address = address;
        this.deliveryAddress = deliveryAddress;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getNip() {
        return nip;
    }

    public Address getAddress() {
        return address;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }


    @Override
    public String toString() {
        return name + " " + lastname + " " + company + " " + nip;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(lastname, customer.lastname) &&
                Objects.equals(company, customer.company) &&
                Objects.equals(nip, customer.nip) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(deliveryAddress, customer.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, lastname, company, nip, address, deliveryAddress);
    }
}
