package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerFormView extends JDialog {
    private JTextField idField = new JTextField();
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

    private JButton submitBtn = new JButton("Send");
    private JButton cancelBtn = new JButton("Cancel");

    public CustomerFormView(Frame parent) {
        super(parent, "Enter Customer Details", true);
        getContentPane().setLayout(new BorderLayout());
        addFieldsToForm();
        addBtnsToForm();
        pack();
        setLocationRelativeTo(parent);
    }

    public String getIdField() {
        return idField.getText();
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getCompanyNameField() {
        return companyNameField.getText();
    }

    public String getNipField() {
        return nipField.getText();
    }

    public String getStreetField() {
        return streetField.getText();
    }

    public String getBuildingNumberField() {
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

    public void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
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
        formPanel.add(submitBtn);
        formPanel.add(cancelBtn);
        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    public void addBtnsToForm() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void submitForm(ActionListener actionListener) {
        submitBtn.addActionListener(actionListener);
    }

    public void cancelForm(ActionListener actionListener) {
        cancelBtn.addActionListener(actionListener);
    }

}
