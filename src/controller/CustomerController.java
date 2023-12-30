package controller;

import model.Customer;
import util.FileUtil;
import util.ValidatorUtil;
import view.CustomerFormView;
import view.CustomerView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerController {
    private final CustomerView view;
    private final CustomerFormView form;
//    private Database database; TODO: Połączenie z bazą danych
    private List<Customer> customers; // najpierw save do tego a potem do db
    public CustomerController(CustomerView view, CustomerFormView form) {
        this.view = view;
        this.form = form;
        this.customers = new ArrayList<>(); // Lista do przechowywania klientów
        initView();
    }
// metoda która wywoluje this.view.loadUsers - laduje klientow do listy w widoku

    private void initView() {

        view.addCustomerToView(new Customer("test"));
        view.addCustomerToView(new Customer("te333st"));

        view.setAddButtonAction(e -> showAddCustomerForm());

        form.submitForm(e -> {

            if(validateFormFields()) {

                form.setVisible(false);
            }

            // Tu dodaje klienta do listy
            // Customer customer = new Customer(id, name, ...);
            // customers.add(customer);
            // view.addCustomerToView(customer);

        });
    }

    private void showAddCustomerForm() {
        form.setVisible(true);
    }

    private boolean validateFormFields() {
        boolean isValidate = true;
        StringBuilder errorMsg = new StringBuilder();

        if(!ValidatorUtil.validateTextField(form.getFirstNameField())) {
            isValidate = false;
            errorMsg.append("First Name Required.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getFirstNameField())) {
            isValidate = false;
            errorMsg.append("Last Name Required.\n");
        }

        if(!errorMsg.isEmpty()) {
            JOptionPane.showMessageDialog(this.form, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValidate;
    }

//    private void showAddCustomerForm() {
//        Customer customer = view.getNewCustomerData(); // Pobierz dane nowego klienta z widoku
//        if (customer != null) {
//            customers.add(customer); // Dodaj klienta do listy
//            view.addCustomerToView(customer); // Dodaj klienta do widoku
//        }
//    }

}
