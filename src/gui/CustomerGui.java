package gui;

import model.Customer;
import service.CustomerService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerGui extends JPanel {
    private JList<Customer> customerList;
    private DefaultListModel<Customer> customerListModel;
    private JButton addButton;
    private JButton removeButton;
    private CustomerService customerService;

    public CustomerGui() {
        customerService = new CustomerService();
        setLayout(new BorderLayout());

        customerListModel = new DefaultListModel<>();
        for (Customer customer : customerService.getAllCustomers()) {
            customerListModel.addElement(customer);
        }
        customerList = new JList<>(customerListModel);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(customerList);
        add(scrollPane, BorderLayout.CENTER);

        addButton = new JButton("Dodaj klienta");
        removeButton = new JButton("Usuń klienta");

        addButton.addActionListener(this::addCustomerAction);
        removeButton.addActionListener(this::removeCustomerAction);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addCustomerAction(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Podaj nazwę klienta:");
        if (name != null && !name.trim().isEmpty()) {
            Customer customer = new Customer(name);
            customerService.addCustomer(customer);
            customerListModel.addElement(customer);
        }
    }

    private void removeCustomerAction(ActionEvent e) {
        Customer selectedCustomer = customerList.getSelectedValue();
        if (selectedCustomer != null) {
            customerService.removeCustomer(selectedCustomer.getId());
            customerListModel.removeElement(selectedCustomer);
        }
    }
}
