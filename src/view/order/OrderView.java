package view.order;

import model.Customer;
import model.Order;
import util.DateTimeUtil;
import view.AbstractView;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderView extends AbstractView {

    private JButton filterButton = new JButton("Filtruj");
    private JButton resetButton = new JButton("Reset");
    private SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private JFormattedTextField startDateSpinner;
    private JFormattedTextField endDateSpinner;
    private JComboBox<Customer> customerComboBox = new JComboBox<>();
    private JTextField minOrderValueField = new JTextField();  // Użytkownik wpisuje minimalną wartość zamówienia tutaj
    private JTextField maxOrderValueField = new JTextField();


    public SimpleDateFormat getDataFormatter() {
        return dataFormatter;
    }

    public JFormattedTextField  getStartDateField() {
        return startDateSpinner;
    }

    public JFormattedTextField  getEndDateField() {
        return endDateSpinner;
    }

    public JComboBox<Customer> getCustomerComboBox() {
        return customerComboBox;
    }

    public JTextField getMinOrderValueField() {
        return minOrderValueField;
    }

    public JTextField getMaxOrderValueField() {
        return maxOrderValueField;
    }

    public OrderView() {
        super(new String[]{"ID", "Data złożenia", "Cena", "Zamawiający"});
        startDateSpinner = DateTimeUtil.createTextFieldWithDataFormat();

        endDateSpinner = DateTimeUtil.createTextFieldWithDataFormat();
        customerComboBox.setPreferredSize(new Dimension(150, 30));
        minOrderValueField.setPreferredSize(new Dimension(100, minOrderValueField.getPreferredSize().height));
        maxOrderValueField.setPreferredSize(new Dimension(100, maxOrderValueField.getPreferredSize().height));

        JPanel filterPanel = new JPanel();
        filterPanel.add(customerComboBox);
        filterPanel.add(startDateSpinner);
        filterPanel.add(endDateSpinner);
        filterPanel.add(minOrderValueField);
        filterPanel.add(maxOrderValueField);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);
        add(filterPanel, BorderLayout.NORTH);


        dataFormatter.setLenient(false);
    }
    @Override
    public void addToView(Object o) {
        Order order  = (Order) o;
        tableModel.addRow(new Object[]{order.getId(), DateTimeUtil.showDate(order.getDate()), order.getOrderTotalPrice(), order.getClient()});
    }



    public void addActionToFilterButton(ActionListener action) {
        filterButton.addActionListener(action);
    }

    public void addActionToResetButton(ActionListener action) {
        resetButton.addActionListener(action);
    }

    public void addActionToCustomerComboBox(PopupMenuListener actionListener) {
        customerComboBox.addPopupMenuListener(actionListener);
    }
}
