package service;

import model.Customer;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class CustomerService {
    private final List<Customer> customers;
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerService() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(int customerId) {
        customers.removeIf(c -> c.getId() == customerId);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
