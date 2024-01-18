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

        addFiltersToView();

        dataFormatter.setLenient(false);
    }

    private void addFiltersToView() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientPanel.add(new JLabel("Klient:"));
        clientPanel.add(customerComboBox);


        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatePanel.add(new JLabel("Data rozpoczęcia:"));
        startDatePanel.add(startDateField);

        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endDatePanel.add(new JLabel("Data zakończenia:"));
        endDatePanel.add(endDateField);

        JPanel minOrderValuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        minOrderValuePanel.add(new JLabel("Min. wartość zamówienia:"));
        minOrderValuePanel.add(minOrderValueField);

        JPanel maxOrderValuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxOrderValuePanel.add(new JLabel("Max. wartość zamówienia:"));
        maxOrderValuePanel.add(maxOrderValueField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(filterButton);
        buttonsPanel.add(resetButton);

        filterPanel.add(clientPanel);
        filterPanel.add(startDatePanel);
        filterPanel.add(endDatePanel);
        filterPanel.add(maxOrderValuePanel);
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

    @Override
    public void addToView(Object o) {
        Order order  = (Order) o;
        tableModel.addRow(new Object[]{order.getId(), DateTimeUtil.showDate(order.getDate()), order.getOrderTotalPrice(), order.getClient()});
    }

    public void addActionToCustomerComboBox(PopupMenuListener actionListener) {
        customerComboBox.addPopupMenuListener(actionListener);
    }
}
