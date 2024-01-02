package controller;

import model.Address;
import model.Customer;
import util.ValidatorUtil;
import view.customer.CustomerFormView;
import view.customer.CustomerView;

import javax.swing.*;

public class CustomerController extends AbstractController<Customer, CustomerView, CustomerFormView> {
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerController(CustomerView view, CustomerFormView form) {
        super(view, form, CUSTOMER_FILE);
        form.showDeliveryPanelYes(e -> toggleDeliveryAddressFields(true));
        form.showDeliveryPanelNo(e -> toggleDeliveryAddressFields(false));
    }


    private void toggleDeliveryAddressFields(boolean show) {
        form.getDeliveryAddressPanel().setVisible(show);
        form.pack();
    }


    @Override
    protected void showDetails(Customer element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();
            details.append("Imię: ").append(element.getName()).append("\n");
            details.append("Nazwisko: ").append(element.getLastname()).append("\n");
            details.append("Firma: ").append(element.getCompany()).append("\n");
            details.append("NIP: ").append(element.getNip()).append("\n\n");
            details.append("Adres:\n").append(element.getAddress()).append("\n\n");
            details.append("Adres dostawy:\n").append(
                    element.getDeliveryAddress() != null ? element.getDeliveryAddress() : "Taki sam jak powyżej"
            ).append("\n\n");

            JOptionPane.showMessageDialog(view, details, "Szczegóły Klienta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Customer create() {
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

    protected boolean validateFormFields() {
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
