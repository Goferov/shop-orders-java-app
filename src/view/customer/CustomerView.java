package view.customer;

import model.Customer;
import util.DateTimeUtil;
import view.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;


public class CustomerView extends AbstractView {

    private SimpleDateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private JFormattedTextField startDateField = DateTimeUtil.createTextFieldWithDataFormat();
    private JFormattedTextField endDateField = DateTimeUtil.createTextFieldWithDataFormat();;
    private JTextField minOrderValueField = new JTextField();

    public JFormattedTextField  getStartDateField() {
        return startDateField;
    }

    public JFormattedTextField  getEndDateField() {
        return endDateField;
    }

    public JTextField getMinOrderValueField() {
        return minOrderValueField;
    }


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
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatePanel.add(new JLabel("Data rozpoczęcia:"));
        startDatePanel.add(startDateField);

        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endDatePanel.add(new JLabel("Data zakończenia:"));
        endDatePanel.add(endDateField);

        JPanel minOrderValuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        minOrderValuePanel.add(new JLabel("Min. wartość zamówienia:"));
        minOrderValuePanel.add(minOrderValueField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(filterButton);
        buttonsPanel.add(resetButton);

        filterPanel.add(startDatePanel);
        filterPanel.add(endDatePanel);
        filterPanel.add(minOrderValuePanel);
        filterPanel.add(buttonsPanel);

        JPanel centeredFilterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centeredFilterPanel.add(filterPanel, gbc);

        add(centeredFilterPanel, BorderLayout.NORTH);
    }

    private void setStylesToFilters() {
        minOrderValueField.setPreferredSize(new Dimension(100, minOrderValueField.getPreferredSize().height));
    }

}
