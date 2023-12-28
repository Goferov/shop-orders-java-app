package gui;

import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerGui extends JPanel {
    private DefaultListModel<Customer> customerModel;
    private JList<Customer> customerList;
    private JFrame frame;

    public CustomerGui() {
        setLayout(new BorderLayout());

        customerModel = new DefaultListModel<>();
        customerList = new JList<>(customerModel);
        add(new JScrollPane(customerList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Dodaj Klienta");
        JButton deleteButton = new JButton("Usuń Klienta");

        addButton.addActionListener(this::addCustomerAction);
        deleteButton.addActionListener(this::deleteCustomerAction);

        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addCustomerAction(ActionEvent e) {
        String name = JOptionPane.showInputDialog(frame, "Podaj imię :", "Nowy Klient", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            Customer newCustomer = new Customer(name);
            customerModel.addElement(newCustomer);
        }
    }

    private void deleteCustomerAction(ActionEvent e) {
        int selectedIndex = customerList.getSelectedIndex();
        if (selectedIndex != -1) {
            customerModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(frame, "Proszę wybrać klienta do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}

