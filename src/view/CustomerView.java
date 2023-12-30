package view;

import model.Customer;
import controller.CustomerController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerView extends JPanel {
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerList;
    private JButton addButton;
    private final String[] userTableColumn = {"FIRST NAME", "LAST NAME"};
    private JTable userTable;

    public CustomerView() {
        initUI();
    }

    private void initUI() {
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        JScrollPane listScrollPane = new JScrollPane(customerList);

        addButton = new JButton("Add Customer");

        setLayout(new BorderLayout());
        add(listScrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    public void showAddCustomerForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));

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

        int result = JOptionPane.showConfirmDialog(null, formPanel,
                "Enter Customer Details", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            // Here you can create your Customer object and Address object from the field values
//            // and then add the customer to your list model.
//            // Customer customer = new Customer(/* parameters from fields */);
//            // customerListModel.addElement(customer);
//
//            // For now, just print the input data as a placeholder
//            System.out.println("ID: " + idField.getText());
//            // ... print other field values ...
//        }
    }

    public void submitUsers(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    // Temporary method to add sample data

    public void setAddButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void addCustomerToView(Customer customer) {
        customerListModel.addElement(customer);
    }
}
