package view.order;

import model.Customer;
import model.Product;
import view.AbstractFormView;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.event.TableModelListener;

public class OrderFormView extends AbstractFormView {

    private JTextField orderDateField = new JTextField();

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

    public String getOrderDateField() {
        return orderDateField.getText();
    }

    @Override
    protected void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(new JLabel("Data złożenia zamówienia:"));
        formPanel.add(orderDateField);

        formPanel.add(new JLabel("Klient:"));

        formPanel.add(customerComboBox);

        formPanel.add(new JLabel("Adres dostawy - Street:"));
        formPanel.add(deliveryStreetField);
        formPanel.add(new JLabel("Adres dostawy - Building Number:"));
        formPanel.add(deliveryHouseNumberField);
        formPanel.add(new JLabel("Adres dostawy - Apartment Number (Optional):"));
        formPanel.add(deliveryApartmentNumberField);
        formPanel.add(new JLabel("Adres dostawy - City:"));
        formPanel.add(deliveryCityField);
        formPanel.add(new JLabel("Adres dostawy - Postal Code:"));
        formPanel.add(deliveryPostalCodeField);
        formPanel.add(new JLabel("Adres dostawy - State:"));
        formPanel.add(deliveryStateField);
        formPanel.add(new JLabel("Adres dostawy - Country:"));
        formPanel.add(deliveryCountryField);
        getContentPane().add(formPanel, BorderLayout.CENTER);
        addProductTableToView();

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
