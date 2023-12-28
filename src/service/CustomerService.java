package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class CustomerService {
    private List<Customer> customerList = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public void removeCustomer(int customerId) {
        customerList.removeIf(c -> c.getId() == customerId);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerList);
    }

}
