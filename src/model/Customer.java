package model;


public class Customer extends AbstractModel {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String lastname;
    private String company; // optional
    private String nip; // optional
    private Address address;
    private Address deliveryAddress; // optional

    public Customer(int id, String name, String lastname, String company, String nip, Address address, Address deliveryAddress) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", lastName='" + lastname + '\'' +
                '}';
    }
}
