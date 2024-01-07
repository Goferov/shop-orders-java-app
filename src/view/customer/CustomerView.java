package view.customer;

import model.Customer;
import util.DateTimeUtil;
import view.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;


public class CustomerView extends AbstractView {

    private SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private JFormattedTextField startDateField = DateTimeUtil.createTextFieldWithDataFormat();
    private JFormattedTextField endDateField = DateTimeUtil.createTextFieldWithDataFormat();;
    private JTextField minOrderValueField = new JTextField();
//    private JTextField maxOrderValueField = new JTextField();

    public SimpleDateFormat getDataFormatter() {
        return dataFormatter;
    }

    public JFormattedTextField  getStartDateField() {
        return startDateField;
    }

    public JFormattedTextField  getEndDateField() {
        return endDateField;
    }

    public JTextField getMinOrderValueField() {
        return minOrderValueField;
    }

//    public JTextField getMaxOrderValueField() {
//        return maxOrderValueField;
//    }

    public CustomerView() {
        super(new String[]{"ID", "Imię", "Nazwisko", "Nazwa firmy", "NIP"});
        addFiltersToView();
        setStylesToFilters();
    }

    @Override
    public void addToView(Object o) {
        Customer customer  = (Customer) o;
        tableModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getLastname(), customer.getCompany(), customer.getNip()});
    }

    private void addFiltersToView() {
        JPanel filterPanel = new JPanel();
        filterPanel.add(startDateField);
        filterPanel.add(endDateField);
        filterPanel.add(minOrderValueField);
//        filterPanel.add(maxOrderValueField);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);
        add(filterPanel, BorderLayout.NORTH);
        dataFormatter.setLenient(false);
    }

    private void setStylesToFilters() {
        minOrderValueField.setPreferredSize(new Dimension(100, minOrderValueField.getPreferredSize().height));
//        maxOrderValueField.setPreferredSize(new Dimension(100, maxOrderValueField.getPreferredSize().height));
    }

}
