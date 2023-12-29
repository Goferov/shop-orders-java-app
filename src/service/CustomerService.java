package service;

import model.Customer;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
public class CustomerService {
    private final List<Customer> customers;
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerService() {
        customers = FileUtil.loadFromFile(CUSTOMER_FILE);
    }

    private void saveCustomers() {
        FileUtil.saveToFile(CUSTOMER_FILE, customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomers();
    }

    public void removeCustomer(int customerId) {
        customers.removeIf(c -> c.getId() == customerId);
        saveCustomers();
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
