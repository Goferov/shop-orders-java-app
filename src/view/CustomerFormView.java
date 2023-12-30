package view;

import javax.swing.*;
import java.awt.*;

public class CustomerFormView {

    private final JPanel formPanel;
    public CustomerFormView() {
        formPanel = new JPanel(new GridLayout(0, 2));

        // Fields for customer information
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField companyNameField = new JTextField();
        JTextField nipField = new JTextField();

        // Fields for address
        JTextField streetField = new JTextField();
        JTextField buildingNumberField = new JTextField();
        JTextField apartmentNumberField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField postalCodeField = new JTextField();
        JTextField stateField = new JTextField();
        JTextField countryField = new JTextField();

        // Add fields to form
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
    }

    public void initForm() {
        JOptionPane.showConfirmDialog(null, formPanel,
                "Enter Customer Details", JOptionPane.OK_CANCEL_OPTION);
    }
}
