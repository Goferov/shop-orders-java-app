package view.order;

import model.Customer;
import model.Product;
import util.FileUtil;
import view.AbstractFormView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;


public class OrderFormView extends AbstractFormView {

    private JTextField orderDateField = new JTextField();

    private JComboBox<Customer> customerComboBox = new JComboBox<>();
    private JTable productTable;
    JButton selectButton = new JButton("Dodaj");;
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

    public JTable getProductTable() {
        return productTable;
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

        JPanel formPanel2 = new JPanel(new BorderLayout());
        String[] columnNames = {"ID Produktu", "Nazwa", "Cena", "Ilość", "Rabat", "Suma Netto", "Suma Brutto"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        JTable orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        formPanel2.add(scrollPane, BorderLayout.CENTER);
        JButton addButton = new JButton("Dodaj Produkt");
        formPanel2.add(addButton, BorderLayout.SOUTH);
        getContentPane().add(formPanel2, BorderLayout.EAST);

        JComboBox<Product> productComboBox = new JComboBox<>();
// Załaduj produkty do comboBoxa. Zakładamy, że masz metodę getProducts().
        List<Product> prods = FileUtil.loadFromFile("products.dat");
        for (Product product : prods) {
            productComboBox.addItem(product);
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Utwórz okno dialogowe z comboBoxem
                JDialog dialog = new JDialog();
                dialog.setTitle("Wybierz produkt");
                dialog.setLayout(new FlowLayout());
                dialog.add(productComboBox);

                dialog.add(selectButton);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productComboBox.getSelectedItem();
                if (selectedProduct != null) {
                    // Dodaj produkt do tabeli
                    Object[] rowData = new Object[]{
                            selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getNetPrice(),
                            1,  // Domyślna ilość
                            0.0,  // Domyślny rabat
                            selectedProduct.getNetPrice(),  // Suma Netto
                            0  // Suma Brutto //selectedProduct.getPrice() * (1 + selectedProduct.getTax()/100)
                    };
                    tableModel.addRow(rowData);

                } else {
                    // Opcjonalnie: Pokaż wiadomość, jeśli żaden produkt nie został wybrany
                }
            }
        });

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

    public void addActionToCustomerList(ActionListener actionListener) {
        customerComboBox.addActionListener(actionListener);
    }

}
