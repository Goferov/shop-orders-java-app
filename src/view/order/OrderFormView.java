package view.order;

import model.Customer;
import model.Product;
import util.FileUtil;
import view.AbstractFormView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

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

        // Product List
        JPanel productPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID Produktu", "Nazwa", "Cena", "Ilość", "Rabat", "Suma Netto", "Suma Brutto"};
        productTableModel = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;
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


// Załaduj produkty do comboBoxa. Zakładamy, że masz metodę getProducts().
        List<Product> prods = FileUtil.loadFromFile("products.dat");
        for (Product product : prods) {
            productComboBox.addItem(product);
        }



        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productComboBox.getSelectedItem();
                if (selectedProduct != null) {
                    Object[] rowData = new Object[]{
                            selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getGrossPrice(),
                            1,  // Domyślna ilość
                            0.0,  // Domyślny rabat
                            selectedProduct.getNetPrice(),  // Suma Netto
                            selectedProduct.getGrossPrice() // Suma Brutto //selectedProduct.getPrice() * (1 + selectedProduct.getTax()/100)
                    };
                    productTableModel.addRow(rowData);
                    productComboBox.removeItem(selectedProduct);

                } else {
                    // Opcjonalnie: Pokaż wiadomość, jeśli żaden produkt nie został wybrany
                }
            }
        });

        productTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Sprawdź, czy zmieniona kolumna to 'Ilość' (indeks 3) lub 'Rabat' (indeks 4)
                if (column == 3 || column == 4) {
                    BigDecimal cenaBrutto = null;
                    BigDecimal ilosc = null;
                    BigDecimal rabat = null;

                    try {
                        cenaBrutto = new BigDecimal(productTableModel.getValueAt(row, 5).toString());
                        ilosc = new BigDecimal(productTableModel.getValueAt(row, 3).toString());
                        rabat = new BigDecimal(productTableModel.getValueAt(row, 4).toString());

                        // Walidacja wartości
                        if (ilosc.compareTo(BigDecimal.ZERO) <= 0) {
                            JOptionPane.showMessageDialog(null, "Ilość musi być większa od 0.");
                            return; // Przerwij dalsze wykonywanie, jeśli ilość nie jest prawidłowa
                        }

                        // Oblicz nową wartość sumy brutto
                        BigDecimal nowaSumaBrutto = cenaBrutto.multiply(ilosc).multiply(BigDecimal.ONE.subtract(rabat));

                        // Aktualizuj model tabeli
                        productTableModel.setValueAt(nowaSumaBrutto, row, 2);
                    } catch (NumberFormatException exception) {
                        // Wyświetl wiadomość, jeśli podana wartość nie jest liczbą
                        JOptionPane.showMessageDialog(null, "Podane wartości muszą być liczbami.");
                    } catch (NullPointerException exception) {
                        // Wyświetl wiadomość, jeśli którakolwiek z wartości jest null
                        JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione.");
                    }
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
        addButton.addActionListener(actionListener);
    }

    private void addActionToAddButton(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void addActionToRemoveButton(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }

}
