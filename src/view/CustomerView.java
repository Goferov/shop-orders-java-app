package view;

import model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerList;
    private JButton addButton;
    private final String[] userTableColumn = {"FIRST NAME", "LAST NAME"};
    private JTable userTable;

    public CustomerView() {
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        JScrollPane listScrollPane = new JScrollPane(customerList);

        addButton = new JButton("Add Customer");

        setLayout(new BorderLayout());
        add(listScrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    public void submitUsers(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }


    public void setAddButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void addCustomerToView(Customer customer) {
        customerListModel.addElement(customer);
    }
}
