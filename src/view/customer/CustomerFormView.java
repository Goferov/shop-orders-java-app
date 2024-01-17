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

    private JPanel deliveryAddressPanel = new JPanel(new GridLayout(0, 2));

    public CustomerFormView(Frame parent) {
        super(parent, "Wprowadź dane klienta");
        addFieldsToForm();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(getSize());
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
        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(2, 2, 2, 2);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.insets = new Insets(2, 2, 2, 2);
        gbcField.weightx = 1.0;

        int row = 0;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("First Name:"), gbcLabel);
        gbcField.gridy = row;
        firstNameField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(firstNameField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Last Name:"), gbcLabel);
        gbcField.gridy = row;
        lastNameField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(lastNameField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Company Name (Optional):"), gbcLabel);
        gbcField.gridy = row;
        companyNameField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(companyNameField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("NIP (Optional):"), gbcLabel);
        gbcField.gridy = row;
        nipField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(nipField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Address - Street:"), gbcLabel);
        gbcField.gridy = row;
        streetField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(streetField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Building Number:"), gbcLabel);
        gbcField.gridy = row;
        buildingNumberField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(buildingNumberField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Apartment Number (Optional):"), gbcLabel);
        gbcField.gridy = row;
        apartmentNumberField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(apartmentNumberField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("City:"), gbcLabel);
        gbcField.gridy = row;
        cityField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(cityField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Postal Code:"), gbcLabel);
        gbcField.gridy = row;
        postalCodeField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(postalCodeField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("State:"), gbcLabel);
        gbcField.gridy = row;
        stateField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(stateField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Country:"), gbcLabel);
        gbcField.gridy = row;
        countryField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(countryField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Inny adres dostawy:"), gbcLabel);
        deliveryAddressGroup.add(deliveryAddressYes);
        deliveryAddressGroup.add(deliveryAddressNo);
        JPanel deliveryOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        deliveryOptions.add(deliveryAddressYes);
        deliveryOptions.add(deliveryAddressNo);
        gbcField.gridy = row;
        formPanel.add(deliveryOptions, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - Street:"), gbcLabel);
        gbcField.gridy = row;
        deliveryStreetField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryStreetField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - Building Number:"), gbcLabel);
        gbcField.gridy = row;
        deliveryHouseNumberField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryHouseNumberField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - Apartment Number (Optional):"), gbcLabel);
        gbcField.gridy = row;
        deliveryApartmentNumberField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryApartmentNumberField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - City:"), gbcLabel);
        gbcField.gridy = row;
        deliveryCityField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryCityField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - Postal Code:"), gbcLabel);
        gbcField.gridy = row;
        deliveryPostalCodeField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryPostalCodeField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - State:"), gbcLabel);
        gbcField.gridy = row;
        deliveryStateField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryStateField, gbcField);
        row++;

        gbcLabel.gridy = row;
        formPanel.add(new JLabel("Delivery Address - Country:"), gbcLabel);
        gbcField.gridy = row;
        deliveryCountryField.setPreferredSize(new Dimension(200, 20));
        formPanel.add(deliveryCountryField, gbcField);

        gbcField.gridy++;
        gbcField.weighty = 1;
        formPanel.add(Box.createVerticalGlue(), gbcField);

        getContentPane().add(formPanel, BorderLayout.CENTER);
    }



    private GridBagConstraints createGbc(int x, int y, int weightx) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = weightx;
        return gbc;
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
