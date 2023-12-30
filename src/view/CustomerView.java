package view;

import model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerList;
    private JButton addButton;
    private JButton removeButton;
//    private final String[] userTableColumn = {"FIRST NAME", "LAST NAME"};
//    private JTable userTable;

    public CustomerView() {
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        JScrollPane listScrollPane = new JScrollPane(customerList);

        addButton = new JButton("Dodaj");
        removeButton = new JButton("Usuń");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        setLayout(new BorderLayout());
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setAddButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void setRemoveButtonAction(ActionListener action) {
        removeButton.addActionListener(action);
    }

    public void addCustomerToView(Customer customer) {
        customerListModel.addElement(customer);
    }

    public void removeCustomerFromView(Customer customer) {
        customerListModel.removeElement(customer);
    }

    public Customer getSelectedCustomer() {
       return customerList.getSelectedValue();
    }
}
