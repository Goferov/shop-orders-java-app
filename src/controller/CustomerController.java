package controller;

import model.Address;
import model.Customer;
import util.FileUtil;
import util.ValidatorUtil;
import view.CustomerFormView;
import view.CustomerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerController {
    private final CustomerView view;
    private final CustomerFormView form;
    private static final String CUSTOMER_FILE = "customers.dat";
//    private Database database; TODO: Połączenie z bazą danych
    private List<Customer> customers;
    private static int currentId = 0;

    public CustomerController(CustomerView view, CustomerFormView form) {
        this.view = view;
        this.form = form;
        this.customers = new ArrayList<>();
        loadCustomers();
        view.setAddButtonAction(e -> showAddCustomerForm());
        view.setRemoveButtonAction(e -> removeCustomer());
        view.setFilterButtonAction(e -> {});
        form.submitForm(e -> addCustomer());
        form.cancelForm(e -> cancelForm());
        form.showDeliveryPanelYes(e -> toggleDeliveryAddressFields(true));
        form.showDeliveryPanelNo(e -> toggleDeliveryAddressFields(false));

        view.setOnDoubleClickAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Integer selectedRow = view.getSelectedCustomer();
                    customerDetails(findCustomerById(selectedRow));
                }
            }
        });
    }




    private void loadCustomers() {
        customers = FileUtil.loadFromFile(CUSTOMER_FILE);
        for(Customer customer : customers) {
            view.addCustomerToView(customer);
        }
    }

    private int generateNextId() {
        int maxId = customers.stream().mapToInt(Customer::getId).max().orElse(0);
        currentId = Math.max(currentId, maxId);
        return currentId + 1;
    }

    private Customer findCustomerById(Integer id) {
        Optional<Customer> customer = customers.stream().filter(c -> c.getId().equals(id)).findFirst();
        return customer.orElse(null);
    }

    private void customerDetails(Customer customer) {
        if(customer != null) {
            StringBuilder details = new StringBuilder();
            details.append("Imię: ").append(customer.getName()).append("\n");
            details.append("Nazwisko: ").append(customer.getLastname()).append("\n");
            details.append("Firma: ").append(customer.getCompany()).append("\n");
            details.append("NIP: ").append(customer.getNip()).append("\n\n");
            details.append("Adres:\n").append(customer.getAddress()).append("\n\n");
            details.append("Adres dostawy:\n").append(
                    customer.getDeliveryAddress() != null ? customer.getDeliveryAddress() : "Taki sam jak powyżej"
            ).append("\n\n");

            JOptionPane.showMessageDialog(view, details, "Szczegóły Klienta", JOptionPane.INFORMATION_MESSAGE);
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
            form.clearFormFields();
            form.setVisible(false);
        }
    }

    private void cancelForm() {
        form.setVisible(false);
    }

    private void toggleDeliveryAddressFields(boolean show) {
        form.getDeliveryAddressPanel().setVisible(show);
        form.pack();
    }

    public void removeCustomer() {
        Integer selectedCustomer = view.getSelectedCustomer();
        if (selectedCustomer != null) {
            customers.removeIf(c -> c.getId().equals(selectedCustomer));
            view.removeCustomerFromView(selectedCustomer);
            saveCustomers();
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
        Address deliveryAddress = null;
        if (form.getdeliveryAddressYes().isSelected()) {
            deliveryAddress = new Address(
                    form.getDeliveryStreetField(),
                    form.getDeliveryHouseNumberField(),
                    form.getDeliveryApartmentNumberField(),
                    form.getDeliveryCityField(),
                    form.getDeliveryPostalCodeField(),
                    form.getDeliveryStateField(),
                    form.getDeliveryCountryField()
            );
        }

        return new Customer(
                generateNextId(),
                form.getNameField(),
                form.getLastNameField(),
                form.getCompanyField(),
                form.getNipField(),
                customerAddress,
                deliveryAddress
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

        if (!ValidatorUtil.validateTextField(form.getCompanyField()) && !form.getNipField().isEmpty()) {
            errorMsg.append("Nazwa firmy jest wymagana, gdy podany jest NIP.\n");
        }

        if (!ValidatorUtil.validateNIP(form.getNipField()) && !form.getCompanyField().isEmpty()) {
            errorMsg.append("NIP jest wymagany, gdy podana jest nazwa firmy.\n");
        }

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

        if (form.getdeliveryAddressYes().isSelected()) {
            if (!ValidatorUtil.validateTextField(form.getDeliveryStreetField())) {
                errorMsg.append("Adres dostawy - nazwa ulicy jest wymagana.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryHouseNumberField())) {
                errorMsg.append("Adres dostawy - numer budynku jest wymagany.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryCityField())) {
                errorMsg.append("Adres dostawy - miejscowość jest wymagana.\n");
            }

            if (!ValidatorUtil.validatePostalCode(form.getDeliveryPostalCodeField())) {
                errorMsg.append("Adres dostawy - kod pocztowy jest niepoprawny.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryStateField())) {
                errorMsg.append("Adres dostawy - województwo jest wymagane.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryCountryField())) {
                errorMsg.append("Adres dostawy - kraj jest wymagany.\n");
            }
        }


        if(!errorMsg.isEmpty()) {
            isValidate = false;
            JOptionPane.showMessageDialog(this.form, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValidate;
    }

}
