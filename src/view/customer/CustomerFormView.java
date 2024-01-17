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

    // delivery address
    private JTextField deliveryStreetField = new JTextField();
    private JTextField deliveryHouseNumberField = new JTextField();
    private JTextField deliveryApartmentNumberField = new JTextField();
    private JTextField deliveryCityField = new JTextField();
    private JTextField deliveryPostalCodeField = new JTextField();
    private JTextField deliveryStateField = new JTextField();
    private JTextField deliveryCountryField = new JTextField();

    private JPanel deliveryAddressPanel = new JPanel(new GridBagLayout());

    public CustomerFormView(Frame parent) {
        super(parent, "Wprowadź dane klienta");
        addFieldsToForm();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(getSize());
    }

    public JTextField getNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getCompanyField() {
        return companyNameField;
    }

    public JTextField getNipField() {
        return nipField;
    }

    public JTextField getStreetField() {
        return streetField;
    }

    public JTextField getHouseNumberField() {
        return buildingNumberField;
    }

    public JTextField getApartmentNumberField() {
        return apartmentNumberField;
    }

    public JTextField getCityField() {
        return cityField;
    }

    public JTextField getPostalCodeField() {
        return postalCodeField;
    }

    public JTextField getStateField() {
        return stateField;
    }

    public JTextField getCountryField() {
        return countryField;
    }

    public JTextField getDeliveryStreetField() {
        return deliveryStreetField;
    }

    public JTextField getDeliveryHouseNumberField() {
        return deliveryHouseNumberField;
    }

    public JTextField getDeliveryApartmentNumberField() {
        return deliveryApartmentNumberField;
    }

    public JTextField getDeliveryCityField() {
        return deliveryCityField;
    }

    public JTextField getDeliveryPostalCodeField() {
        return deliveryPostalCodeField;
    }

    public JTextField getDeliveryStateField() {
        return deliveryStateField;
    }

    public JTextField getDeliveryCountryField() {
        return deliveryCountryField;
    }

    public JRadioButton getdeliveryAddressYes() {
        return deliveryAddressYes;
    }

    public JPanel getDeliveryAddressPanel() {
        return deliveryAddressPanel;
    }

    public void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = 1.0;

        addField(formPanel, "Imię:", firstNameField, gbc);
        addField(formPanel, "Nazwisko:", lastNameField, gbc);
        addField(formPanel, "Nazwa firmy (opcjonalne):", companyNameField, gbc);
        addField(formPanel, "NIP (opcjonalne):", nipField, gbc);
        addField(formPanel, "Ulica:", streetField, gbc);
        addField(formPanel, "Numer budynku:", buildingNumberField, gbc);
        addField(formPanel, "Numer mieszkania (opcjonalne):", apartmentNumberField, gbc);
        addField(formPanel, "Miasto:", cityField, gbc);
        addField(formPanel, "Kod pocztowy:", postalCodeField, gbc);
        addField(formPanel, "Województwo:", stateField, gbc);
        addField(formPanel, "Kraj:", countryField, gbc);

        addDeliveryAddressRadioBtns(formPanel, gbc);

        addField(formPanel, "Adres dostawy - Ulica:", deliveryStreetField, gbc);
        addField(formPanel, "Adres dostawy - Numer budynku:", deliveryHouseNumberField, gbc);
        addField(formPanel, "Adres dostawy - Numer mieszkania (opcjonalne):", deliveryApartmentNumberField, gbc);
        addField(formPanel, "Adres dostawy - Miasto:", deliveryCityField, gbc);
        addField(formPanel, "Adres dostawy - Kod pocztowy:", deliveryPostalCodeField, gbc);
        addField(formPanel, "Adres dostawy - Województwo:", deliveryStateField, gbc);
        addField(formPanel, "Adres dostawy - Kraj:", deliveryCountryField, gbc);
        setDeliveryAddressFieldsEnabled(false);
        getContentPane().add(formPanel, BorderLayout.CENTER);
    }



    private void addDeliveryAddressRadioBtns(JPanel panel, GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        panel.add(new JLabel("Inny adres dostawy:"), gbc);

        deliveryAddressGroup.add(deliveryAddressYes);
        deliveryAddressGroup.add(deliveryAddressNo);
        JPanel deliveryOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        deliveryOptions.add(deliveryAddressYes);
        deliveryOptions.add(deliveryAddressNo);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(deliveryOptions, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;

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

    public void setDeliveryAddressFieldsEnabled(boolean enabled) {
        deliveryStreetField.setEnabled(enabled);
        deliveryHouseNumberField.setEnabled(enabled);
        deliveryApartmentNumberField.setEnabled(enabled);
        deliveryCityField.setEnabled(enabled);
        deliveryPostalCodeField.setEnabled(enabled);
        deliveryStateField.setEnabled(enabled);
        deliveryCountryField.setEnabled(enabled);
    }

    public void showDeliveryPanelYes(ActionListener actionListener) {
        deliveryAddressYes.addActionListener(actionListener);
    }

    public void showDeliveryPanelNo(ActionListener actionListener) {
        deliveryAddressNo.addActionListener(actionListener);
    }

}
