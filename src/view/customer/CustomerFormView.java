package view.customer;

import view.AbstractFormView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerFormView extends AbstractFormView {
    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField companyNameField = new JTextField();
    private JTextField nipField = new JTextField();
    private JTextField streetField = new JTextField();
    private JTextField buildingNumberField = new JTextField();
    private JTextField apartmentNumberField = new JTextField();
    private JTextField cityField = new JTextField();
    private JTextField postalCodeField = new JTextField();
    private JTextField stateField = new JTextField();
    private JTextField countryField = new JTextField();

    private JRadioButton deliveryAddressYes = new JRadioButton("Tak");
    private JRadioButton deliveryAddressNo = new JRadioButton("Nie", true);
    private ButtonGroup deliveryAddressGroup = new ButtonGroup();

    // Pola dla adresu dostawy
    private JTextField deliveryStreetField = new JTextField();
    private JTextField deliveryHouseNumberField = new JTextField();
    private JTextField deliveryApartmentNumberField = new JTextField();
    private JTextField deliveryCityField = new JTextField();
    private JTextField deliveryPostalCodeField = new JTextField();
    private JTextField deliveryStateField = new JTextField();
    private JTextField deliveryCountryField = new JTextField();

    private JPanel deliveryAddressPanel = new JPanel(new GridLayout(0, 2));

    public CustomerFormView(Frame parent) {
        super(parent, "Enter Customer Details");
        addFieldsToForm();
        addDeliveryAddressFields();
        addDeliveryOptionToForm();
        pack();
        setLocationRelativeTo(parent);
    }

    public String getNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getCompanyField() {
        return companyNameField.getText();
    }

    public String getNipField() {
        return nipField.getText();
    }

    public String getStreetField() {
        return streetField.getText();
    }

    public String getHouseNumberField() {
        return buildingNumberField.getText();
    }

    public String getApartmentNumberField() {
        return apartmentNumberField.getText();
    }

    public String getCityField() {
        return cityField.getText();
    }

    public String getPostalCodeField() {
        return postalCodeField.getText();
    }

    public String getStateField() {
        return stateField.getText();
    }

    public String getCountryField() {
        return countryField.getText();
    }

    public String getDeliveryStreetField() {
        return deliveryStreetField.getText();
    }

    public String getDeliveryHouseNumberField() {
        return deliveryHouseNumberField.getText();
    }

    public String getDeliveryApartmentNumberField() {
        return deliveryApartmentNumberField.getText();
    }

    public String getDeliveryCityField() {
        return deliveryCityField.getText();
    }

    public String getDeliveryPostalCodeField() {
        return deliveryPostalCodeField.getText();
    }

    public String getDeliveryStateField() {
        return deliveryStateField.getText();
    }

    public String getDeliveryCountryField() {
        return deliveryCountryField.getText();
    }

    public JRadioButton getdeliveryAddressYes() {
        return deliveryAddressYes;
    }

    public JPanel getDeliveryAddressPanel() {
        return deliveryAddressPanel;
    }

    public void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Company Name (Optional):"));
        formPanel.add(companyNameField);
        formPanel.add(new JLabel("NIP (Optional):"));
        formPanel.add(nipField);
        formPanel.add(new JLabel("Address - Street:"));
        formPanel.add(streetField);
        formPanel.add(new JLabel("Building Number:"));
        formPanel.add(buildingNumberField);
        formPanel.add(new JLabel("Apartment Number (Optional):"));
        formPanel.add(apartmentNumberField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(new JLabel("Postal Code:"));
        formPanel.add(postalCodeField);
        formPanel.add(new JLabel("State:"));
        formPanel.add(stateField);
        formPanel.add(new JLabel("Country:"));
        formPanel.add(countryField);
        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    public void addDeliveryOptionToForm() {
        deliveryAddressGroup.add(deliveryAddressYes);
        deliveryAddressGroup.add(deliveryAddressNo);

        JPanel deliveryOptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deliveryOptionPanel.add(new JLabel("Inny adres dostawy: "));
        deliveryOptionPanel.add(deliveryAddressYes);
        deliveryOptionPanel.add(deliveryAddressNo);

        getContentPane().add(deliveryOptionPanel, BorderLayout.NORTH);

        deliveryAddressPanel.setVisible(false);
    }



    public void addDeliveryAddressFields() {
        deliveryAddressPanel.add(new JLabel("Delivery Address - Street:"));
        deliveryAddressPanel.add(deliveryStreetField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - Building Number:"));
        deliveryAddressPanel.add(deliveryHouseNumberField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - Apartment Number (Optional):"));
        deliveryAddressPanel.add(deliveryApartmentNumberField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - City:"));
        deliveryAddressPanel.add(deliveryCityField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - Postal Code:"));
        deliveryAddressPanel.add(deliveryPostalCodeField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - State:"));
        deliveryAddressPanel.add(deliveryStateField);

        deliveryAddressPanel.add(new JLabel("Delivery Address - Country:"));
        deliveryAddressPanel.add(deliveryCountryField);

        getContentPane().add(deliveryAddressPanel, BorderLayout.EAST);
        deliveryAddressPanel.setVisible(false); // Domyslnie ukryte
    }

    public void clearFormFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        companyNameField.setText("");
        nipField.setText("");
        streetField.setText("");
        buildingNumberField.setText("");
        apartmentNumberField.setText("");
        cityField.setText("");
        postalCodeField.setText("");
        stateField.setText("");
        countryField.setText("");
        deliveryStreetField.setText("");
        deliveryHouseNumberField.setText("");
        deliveryApartmentNumberField.setText("");
        deliveryCityField.setText("");
        deliveryPostalCodeField.setText("");
        deliveryStateField.setText("");
        deliveryCountryField.setText("");
    }

    public void showDeliveryPanelYes(ActionListener actionListener) {
        deliveryAddressYes.addActionListener(actionListener);
    }

    public void showDeliveryPanelNo(ActionListener actionListener) {
        deliveryAddressNo.addActionListener(actionListener);
    }

}
