package model;

public class Customer {
    private int id;
    private String name;
    private String lastname;
    private String company; // optional
    private String nip; // optional
    private Address address;
    private Address deliveryAddress; // optional

    public int getId() {
        return id;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        Customer customer = (Customer) obj;
//        return id != null && id.equals(customer.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
}
