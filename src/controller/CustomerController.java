package controller;

import model.Address;
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
    private static final String CUSTOMER_FILE = "customers.dat";
//    private Database database; TODO: Połączenie z bazą danych
    private List<Customer> customers;
    public CustomerController(CustomerView view, CustomerFormView form) {
        this.view = view;
        this.form = form;
        this.customers = new ArrayList<>();
        loadCustomers();
        view.setAddButtonAction(e -> showAddCustomerForm());
        view.setRemoveButtonAction(e -> removeCustomer());
        form.submitForm(e -> addCustomer());
    }


    private void loadCustomers() {
        customers = FileUtil.loadFromFile(CUSTOMER_FILE);
        for(Customer customer : customers) {
            view.addCustomerToView(customer);
        }
    }

    private void saveCustomers() {
        FileUtil.saveToFile(CUSTOMER_FILE, customers);
    }

    private void showAddCustomerForm() {
        form.setVisible(true);
    }

    private void addCustomer() {
        if(validateFormFields()) {
            Customer customer = createNewCustomer();
            customers.add(customer);
            view.addCustomerToView(customer);
            saveCustomers();
            form.setVisible(false);
        }
    }

    public void removeCustomer() {
        try {
            customers.removeIf(c -> c.getId() == view.getSelectedCustomer());
            view.removeCustomerFromView(view.getSelectedCustomer());
            saveCustomers();
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this.form, "Nie wybrano żadnego elementu z listy", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private Customer createNewCustomer() {
        Address customerAddress = new Address(
                form.getStreetField(),
                form.getHouseNumberField(),
                form.getApartmentNumberField(),
                form.getCityField(),
                form.getPostalCodeField(),
                form.getStateField(),
                form.getCountryField()
        );
        return new Customer(
                2,
                form.getNameField(),
                form.getLastNameField(),
                form.getCompanyField(),
                form.getNipField(),
                customerAddress
        );
    }

    private boolean validateFormFields() {
        boolean isValidate = true;
        StringBuilder errorMsg = new StringBuilder();

        if(!ValidatorUtil.validateTextField(form.getNameField())) {
            errorMsg.append("Imię jest wymagane.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getLastNameField())) {
            errorMsg.append("Nazwisko jest wymagane.\n");
        }

        // jesli któreś z pól nip lub firma nie są null to czy oba powinny być wymagane?

        if(!ValidatorUtil.validateNIP(form.getNipField())) {
            errorMsg.append("NIP jest niepoprawny.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getStreetField())) {
            errorMsg.append("Nazwa ulicy jest wymagana.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getHouseNumberField())) {
            errorMsg.append("Numer budynku jest wymagany.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getCityField())) {
            errorMsg.append("Miejscowość jest wymagana.\n");
        }

        if(!ValidatorUtil.validatePostalCode(form.getPostalCodeField())) {
            errorMsg.append("Kod pocztowy jest niepoprawny.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getStateField())) {
            errorMsg.append("Województwo jest niepoprawne.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getCountryField())) {
            errorMsg.append("Kraj jest wymagany.\n");
        }

        if(!errorMsg.isEmpty()) {
            isValidate = false;
            JOptionPane.showMessageDialog(this.form, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValidate;
    }

}
