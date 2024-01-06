package view.order;

import model.Customer;
import model.Order;
import util.DateTimeUtil;
import view.AbstractView;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class OrderView extends AbstractView {

    private JButton filterButton = new JButton("Filtruj");
    private JButton resetButton = new JButton("Reset");
    private SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JComboBox<Customer> customerComboBox = new JComboBox<>();


    public SimpleDateFormat getDataFormatter() {
        return dataFormatter;
    }

    public JSpinner getStartDateSpinner() {
        return startDateSpinner;
    }

    public JSpinner getEndDateSpinner() {
        return endDateSpinner;
    }

    public JComboBox<Customer> getCustomerComboBox() {
        return customerComboBox;
    }

    public OrderView() {
        super(new String[]{"ID", "Data złożenia", "Cena", "Zamawiający"});
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd-MM-yyyy");
        startDateSpinner.setEditor(startDateEditor);

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd-MM-yyyy");
        endDateSpinner.setEditor(endDateEditor);
        customerComboBox.setPreferredSize(new Dimension(150, 30));
        JPanel filterPanel = new JPanel();
        filterPanel.add(customerComboBox);
        filterPanel.add(startDateSpinner);
        filterPanel.add(endDateSpinner);
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
