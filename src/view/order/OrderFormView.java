package view.order;

import model.Customer;
import model.Product;
import util.DateTimeUtil;
import view.AbstractFormView;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.event.TableModelListener;

public class OrderFormView extends AbstractFormView {

    private JFormattedTextField orderDateField = DateTimeUtil.createTextFieldWithDataFormat();

    private JComboBox<Customer> customerComboBox = new JComboBox<>();
    private JTable productTable;
    private DefaultTableModel productTableModel;
    public JButton selectButton = new JButton("Dodaj");;
    public JComboBox<Product> productComboBox = new JComboBox<>();
    public JButton addButton = new JButton("Dodaj Produkt");
    public JButton removeButton = new JButton("Usuń");
    private JTextField deliveryStreetField = new JTextField();
    private JTextField deliveryHouseNumberField = new JTextField();
    private JTextField deliveryApartmentNumberField = new JTextField();
    private JTextField deliveryCityField = new JTextField();
    private JTextField deliveryPostalCodeField = new JTextField();
    private JTextField deliveryStateField = new JTextField();
    private JTextField deliveryCountryField = new JTextField();

    public OrderFormView(Frame parent) {
        super(parent, "Dodaj nowe zamówienie");
        addFieldsToForm();
        pack();
        setLocationRelativeTo(parent);
    }

    public JComboBox<Customer> getCustomerComboBox() {
        return customerComboBox;
    }

    public JComboBox<Product> getProductComboBox() {
        return productComboBox;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getproductTableModel() {
        return productTableModel;
    }

    public JTextField getDeliveryStreetField() {
        return deliveryStreetField;
    }


    public JTextField getDeliveryHouseNumberField() {
        return deliveryHouseNumberField;
    }

    public JTextField getDeliveryApartmentNumberField() {
        return deliveryApartmentNumberField;
    }

    public JTextField getDeliveryCityField() {
        return deliveryCityField;
    }

    public JTextField getDeliveryPostalCodeField() {
        return deliveryPostalCodeField;
    }

    public JTextField getDeliveryStateField() {
        return deliveryStateField;
    }

    public JTextField getDeliveryCountryField() {
        return deliveryCountryField;
    }

    public JTextField getOrderDateField() {
        return orderDateField;
    }

    @Override
    protected void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addField(formPanel, "Data złożenia zamówienia::", orderDateField, gbc);
        addField(formPanel, "Klient:", customerComboBox, gbc);
        addField(formPanel, "Adres dostawy - Ulica:", deliveryStreetField, gbc);
        addField(formPanel, "Adres dostawy - Numer domu:", deliveryHouseNumberField, gbc);
        addField(formPanel, "Adres dostawy - Numer mieszkania:", deliveryApartmentNumberField, gbc);
        addField(formPanel, "Adres dostawy - Miejscowość:", deliveryCityField, gbc);
        addField(formPanel, "Adres dostawy - Kod pocztowy:", deliveryPostalCodeField, gbc);
        addField(formPanel, "Adres dostawy - Województwo:", deliveryStateField, gbc);
        addField(formPanel, "Adres dostawy - Państwo:", deliveryCountryField, gbc);

        getContentPane().add(formPanel, BorderLayout.CENTER);
        addProductTableToView();
        productComboBox.setPreferredSize(new Dimension(350, 20));
    }

    private void addProductTableToView() {
        JPanel productPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Nazwa", "Netto", "Brutto", "Ilość", "Rabat", "Suma"};
        productTableModel = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }
        };
        productTable = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        productPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        productPanel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(productPanel, BorderLayout.EAST);

        addActionToAddButton(e -> createDialogWithProductComboBox());
    }

    @Override
    public void clearFormFields() {
        deliveryStreetField.setText("");
        deliveryHouseNumberField.setText("");
        deliveryApartmentNumberField.setText("");
        deliveryCityField.setText("");
        deliveryPostalCodeField.setText("");
        deliveryStateField.setText("");
        deliveryCountryField.setText("");
        orderDateField.setText("");
        productTableModel.setRowCount(0);
    }

    private void createDialogWithProductComboBox() {
        JDialog dialog = new JDialog(this, "Dodaj produkt do zamówienia", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(productComboBox);

        dialog.add(selectButton);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void addActionToCustomerList(ActionListener actionListener) {
        customerComboBox.addActionListener(actionListener);
    }

    public void addActionToAddButton(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void addActionToSelectButton(ActionListener actionListener) {
        selectButton.addActionListener(actionListener);
    }

    public void addActionToRemoveButton(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }

    public void addActionToProductTableModel(TableModelListener actionListener) {
        productTableModel.addTableModelListener(actionListener);
    }

    public void addActionToCustomerComboBox(PopupMenuListener actionListener) {
        customerComboBox.addPopupMenuListener(actionListener);
    }

}
