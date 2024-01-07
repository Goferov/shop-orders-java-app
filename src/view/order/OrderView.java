package view.order;

import model.Customer;
import model.Order;
import util.DateTimeUtil;
import view.AbstractView;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.text.SimpleDateFormat;

public class OrderView extends AbstractView {

    private SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private JFormattedTextField startDateField = DateTimeUtil.createTextFieldWithDataFormat();
    private JFormattedTextField endDateField = DateTimeUtil.createTextFieldWithDataFormat();;
    private JComboBox<Customer> customerComboBox = new JComboBox<>();
    private JTextField minOrderValueField = new JTextField();
    private JTextField maxOrderValueField = new JTextField();


    public SimpleDateFormat getDataFormatter() {
        return dataFormatter;
    }

    public JFormattedTextField  getStartDateField() {
        return startDateField;
    }

    public JFormattedTextField  getEndDateField() {
        return endDateField;
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

        customerComboBox.setPreferredSize(new Dimension(150, 30));
        minOrderValueField.setPreferredSize(new Dimension(100, minOrderValueField.getPreferredSize().height));
        maxOrderValueField.setPreferredSize(new Dimension(100, maxOrderValueField.getPreferredSize().height));

        JPanel filterPanel = new JPanel();
        filterPanel.add(customerComboBox);
        filterPanel.add(startDateField);
        filterPanel.add(endDateField);
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

    public void addActionToCustomerComboBox(PopupMenuListener actionListener) {
        customerComboBox.addPopupMenuListener(actionListener);
    }
}
