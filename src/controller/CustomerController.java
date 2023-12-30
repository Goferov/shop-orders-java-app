package controller;

import model.Customer;
import util.FileUtil;
import view.CustomerFormView;
import view.CustomerView;

import java.util.ArrayList;
import java.util.List;
public class CustomerController {
    private CustomerView view;
    private CustomerFormView form;
    private List<Customer> customers; // najpierw save do tego a potem do db
    public CustomerController(CustomerView view) {
        this.view = view;
        this.form = new CustomerFormView();
        this.customers = new ArrayList<>(); // Lista do przechowywania klientów
        initializeView();
    }
// metoda która wywoluje this.view.loadUsers - laduje klientow do listy w widoku

    private void initializeView() {
        // Możesz tutaj zainicjalizować widok dodatkowymi ustawieniami jeśli jest to potrzebne
        // Na przykład, załaduj początkowych klientów do widoku, jeśli są dostępni
        view.addCustomerToView(new Customer("test")); // Dodaj przykładowego klienta
        view.addCustomerToView(new Customer("te333st")); // Dodaj przykładowego klienta

        // Dodaj akcję dla przycisku dodawania klienta
        view.setAddButtonAction(e -> showAddCustomerForm());
    }

    private void showAddCustomerForm() {
        // Wywołanie metody z widoku, która tworzy i wyświetla formularz
        form.initForm();

        // Tutaj obsłuż logikę tworzenia nowego klienta i dodaj go do listy
        // Na przykład:
        // Customer customer = new Customer(id, name, ...);
        // customers.add(customer);
        // view.addCustomerToView(customer);
    }

//    private void showAddCustomerForm() {
//        Customer customer = view.getNewCustomerData(); // Pobierz dane nowego klienta z widoku
//        if (customer != null) {
//            customers.add(customer); // Dodaj klienta do listy
//            view.addCustomerToView(customer); // Dodaj klienta do widoku
//        }
//    }

}
