package controller;

import model.*;
import util.DateTimeUtil;
import util.FileUtil;
import util.ValidatorUtil;
import view.order.OrderFormView;
import view.order.OrderView;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class OrderController extends AbstractController<Order, OrderView, OrderFormView> {

    private Customer selectedCustomer;
    private Customer latestCustomer;
    private Date orderDate;
    private int quantity;
    private int discount;
    private List<ItemsList> itemsList = new ArrayList<>();

    protected OrderController(OrderView view, OrderFormView orderFormView) {
        super(view, orderFormView, "orders.dat");
        form.addActionToRemoveButton(e -> removeProductFromOrderList());
        form.addActionToSelectButton(e -> addProductsToTable());
        form.addActionToProductTableModel(this::calculateTotalSum);
        form.addActionToAddButton(e -> updateAvailableProducts());
        form.addActionToCustomerComboBox(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                addCustomersToFormComboBox(form.getCustomerComboBox());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                updateDeliveryAddressFields();
            }

            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        view.addActionToCustomerComboBox(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                addCustomersToComboBox(view.getCustomerComboBox());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        view.addActionToFilterButton(e -> search());
        view.addActionToResetButton(e -> resetFilter());

        form.getProductComboBox().setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Product) {
                    Product product = (Product) value;
                    setText(product.getName());
                }

                return this;
            }
        });
        setSorterToBigDecimalValue(2);
    }


    @Override
    protected void showDetails(Order element) {
        if (element != null) {
            JTextArea textArea = new JTextArea(15, 50);
            textArea.setText(createOrderDetailsText(element));
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(view, scrollPane, "Szczegóły zamówienia", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String createOrderDetailsText(Order element) {
        StringBuilder details = new StringBuilder();

        details.append("Identyfikator: \n").append(element.getId()).append("\n\n");
        details.append("Data: \n").append(DateTimeUtil.showDate(element.getDate())).append("\n\n");
        details.append("Klient: \n").append(element.getClient()).append("\n\n");
        details.append("Produkty: \n");
        details.append(String.format("%-10s %-30s %-10s %-10s %-15s %-15s\n", "ID", "Nazwa", "Ilość", "Rabat", "Netto", "Brutto"));
        details.append("----------------------------------------------------------------------------------------\n");
        for (ItemsList item : element.getItemsList()) {
            details.append(String.format("%-10d %-30s %-10d %-10d %-15.2f %-15.2f\n",
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getDiscount(),
                    item.getNetTotal(),
                    item.getGrossTotal()));
        }
        details.append("\n");
        details.append("Cena całkowita: \n").append(element.getOrderTotalPrice()).append(" zł\n\n");
        details.append("Adres dostawy:\n").append(element.getDeliveryAddress()).append("\n\n");

        return details.toString();
    }

    @Override
    protected Order create() {
        List<ItemsList> orderItems = new ArrayList<>(itemsList);

        return new Order(
                generateNextId(),
                orderDate,
                orderItems,
                selectedCustomer,
                createAddress()
        );
    }




    @Override
    protected void validateFormFields() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            orderDate = sdf.parse(form.getOrderDateField().getText());
            form.getOrderDateField().setBackground(Color.white);
        } catch (Exception e) {
            errorMsg.append("Data powinna być zapisana w formacie dd-MM-yyyy.\n");
            form.getOrderDateField().setBackground(Color.pink);
        }

        if(selectedCustomer == null) {
            errorMsg.append("Nie wybrano klienta.\n");
            form.getCustomerComboBox().setBackground(Color.pink);
        }
        else {
            form.getCustomerComboBox().setBackground(Color.white);
        }

        validateAndColorField(form.getDeliveryStreetField(), "Adres dostawy - nazwa ulicy jest wymagana.\n");
        validateAndColorField(form.getDeliveryHouseNumberField(), "Adres dostawy - numer budynku jest wymagany.\n");
        validateAndColorField(form.getDeliveryCityField(), "Adres dostawy - miejscowość jest wymagana.\n");
        validateAndColorField(form.getDeliveryStateField(), "Adres dostawy - województwo jest wymagane.\n");
        validateAndColorField(form.getDeliveryCountryField(), "Adres dostawy - kraj jest wymagany.\n");

        if(!ValidatorUtil.validatePostalCode(form.getDeliveryPostalCodeField().getText())) {
            errorMsg.append("Adres dostawy - Kod pocztowy jest niepoprawny.\n");
            form.getDeliveryPostalCodeField().setBackground(Color.PINK);
        }
        else {
            form.getDeliveryPostalCodeField().setBackground(Color.WHITE);
        }

        validateAndProcessProductTable();
    }

    private Address createAddress() {
        return new Address(
                form.getDeliveryStreetField().getText(),
                form.getDeliveryHouseNumberField().getText(),
                form.getDeliveryApartmentNumberField().getText(),
                form.getDeliveryCityField().getText(),
                form.getDeliveryPostalCodeField().getText(),
                form.getDeliveryStateField().getText(),
                form.getDeliveryCountryField().getText()
        );
    }

    private void validateAndProcessProductTable() {
        DefaultTableModel tableModel = form.getproductTableModel();
        itemsList.clear();

        if (tableModel.getRowCount() == 0) {
            errorMsg.append("Lista produktów jest pusta. Dodaj przynajmniej jeden produkt.\n");
            return;
        }

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            try {
                Integer productId = (Integer) tableModel.getValueAt(row, 0);;
                String productName = (String) tableModel.getValueAt(row, 1);
                BigDecimal nettoPrice  = new BigDecimal(tableModel.getValueAt(row, 2).toString());
                BigDecimal grossPrice = new BigDecimal(tableModel.getValueAt(row, 3).toString());
                quantity = Integer.parseInt(tableModel.getValueAt(row, 4).toString());
                discount = Integer.parseInt(tableModel.getValueAt(row, 5).toString());

                if (quantity <= 0) {
                    errorMsg.append("Ilość musi być większa od 0: " + productName + ".\n");
                    continue;
                }

                if (discount < 0 || discount > 100) {
                    errorMsg.append("Rabat musi być większy od 0: " + productName + ".\n");
                }

                addProductToItemList(productId, quantity, productName, discount, calculateTotal(nettoPrice, quantity), calculateTotal(grossPrice, quantity));

            } catch (NumberFormatException e) {
                errorMsg.append("Wartości muszą być liczbami dla produktu: " + tableModel.getValueAt(row, 1) + ".\n");
            } catch (NullPointerException e) {
                errorMsg.append("Wszystkie pola muszą być wypełnione dla produktu: " + tableModel.getValueAt(row, 1) + ".\n");
            }
        }
    }

    private void addProductToItemList(Integer id, int quantity, String name, int discount, BigDecimal netTotal, BigDecimal grossTotal) {
        itemsList.add(new ItemsList(id, quantity, name, discount, netTotal, grossTotal));
    }

    private List<Customer> getCustomers() {
       return FileUtil.loadFromFile("customers.dat");
    }

    private List<Product> getProducts() {
        return FileUtil.loadFromFile("products.dat");
    }

    private void addCustomersToFormComboBox(JComboBox<Customer> comboBox) {
        addCustomersToComboBox(comboBox);

        if (latestCustomer != null) {
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                if (comboBox.getItemAt(i).equals(latestCustomer)) {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void addCustomersToComboBox(JComboBox<Customer> comboBox) {
        comboBox.removeAllItems();
        List<Customer> dataList = getCustomers();
        for(Customer customer : dataList) {
            comboBox.addItem(customer);
        }
        comboBox.setSelectedIndex(-1);
    }

    private void updateDeliveryAddressFields() {
        if (!form.getCustomerComboBox().isFocusOwner()) {
            return;
        }

        selectedCustomer = (Customer) form.getCustomerComboBox().getSelectedItem();

        if (selectedCustomer != null) {
            latestCustomer = selectedCustomer;
            Address addr = null;
            if(selectedCustomer.getDeliveryAddress() != null) {
                addr = selectedCustomer.getDeliveryAddress();
            }
            else {
                addr = selectedCustomer.getAddress();
            }

            form.getDeliveryStreetField().setText(addr.getStreet());
            form.getDeliveryHouseNumberField().setText(addr.getHouseNumber());
            form.getDeliveryApartmentNumberField().setText(addr.getApartmentNumber());
            form.getDeliveryCityField().setText(addr.getCity());
            form.getDeliveryPostalCodeField().setText(addr.getPostalCode());
            form.getDeliveryStateField().setText(addr.getState());
            form.getDeliveryCountryField().setText(addr.getCountry());
        }
    }

    private Integer getSelectedElementFromProductList() {
        int selectedRow = form.getProductTable().getSelectedRow();
        if (selectedRow >= 0) {
            return (Integer) form.getproductTableModel().getValueAt(selectedRow, 0);
        }
        return null;
    }

    private void addProductsToTable() {
        Product selectedProduct = (Product) form.getProductComboBox().getSelectedItem();
        if (selectedProduct != null) {
            Object[] rowData = new Object[] {
                    selectedProduct.getId(),
                    selectedProduct.getName(),
                    selectedProduct.getNetPrice(),
                    selectedProduct.getGrossPrice(),
                    1,  // default qty
                    0,  // default discount
                    selectedProduct.getGrossPrice(),
            };
            form.getproductTableModel().addRow(rowData);
            form.getProductComboBox().removeItem(selectedProduct);
        }
    }

    private void updateAvailableProducts() {
        List<Product> latestProducts = getProducts();

        Set<Integer> productIdsInOrder = new HashSet<>();
        for (int i = 0; i < form.getproductTableModel().getRowCount(); i++) {
            Integer productId = (Integer) form.getproductTableModel().getValueAt(i, 0);
            productIdsInOrder.add(productId);
        }

        form.getProductComboBox().removeAllItems();

        for (Product product : latestProducts) {
            if (!productIdsInOrder.contains(product.getId())) {
                form.getProductComboBox().addItem(product);
            }
        }
    }

    private BigDecimal calculateTotal(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void calculateTotalSum(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (column == 4 || column == 5) {
            BigDecimal cenaBrutto = null;
            int quantity = 0;
            int discount = 0;

            try {
                cenaBrutto = new BigDecimal(form.getproductTableModel().getValueAt(row, 3).toString());
                quantity = Integer.parseInt(form.getproductTableModel().getValueAt(row, 4).toString());

                Object rabatObj = form.getproductTableModel().getValueAt(row, 5);
                if (rabatObj != null) {
                    discount = Integer.parseInt(rabatObj.toString());

                    if (discount < 0 || discount > 100) {
                        JOptionPane.showMessageDialog(null, "Rabat musi być wartością od 0 do 100.");
                        return;
                    }
                }

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Ilość musi być większa od 0.");
                    return;
                }

                BigDecimal rabatDecimal = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
                BigDecimal newGrossPrice = cenaBrutto.multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.ONE.subtract(rabatDecimal));

                form.getproductTableModel().setValueAt(newGrossPrice.setScale(2, BigDecimal.ROUND_HALF_UP), row, 6);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Podane wartości muszą być liczbami.");
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione.");
            }
        }
    }

    private void removeProductFromOrderList() {
        Integer selectedElement = getSelectedElementFromProductList();
        if (selectedElement != null) {
            for (int i = 0; i < form.getproductTableModel().getRowCount(); i++) {
                if (form.getproductTableModel().getValueAt(i, 0).equals(selectedElement)) {
                    form.getproductTableModel().removeRow(i);
                    updateAvailableProducts();
                    break;
                }
            }
        }
    }

    private void search() {
        String startDateString = view.getStartDateField().getText();
        String endDateString = view.getEndDateField().getText();

        Date startDate = DateTimeUtil.parseDate(startDateString);
        Date endDate = DateTimeUtil.parseDate(endDateString);

        Customer selectedCustomer = (Customer) view.getCustomerComboBox().getSelectedItem();
        BigDecimal minOrderValue = null;
        BigDecimal maxOrderValue = null;

        try {
            if (!view.getMinOrderValueField().getText().isEmpty()) {
                minOrderValue = new BigDecimal(view.getMinOrderValueField().getText());
            }
            if (!view.getMaxOrderValueField().getText().isEmpty()) {
                maxOrderValue = new BigDecimal(view.getMaxOrderValueField().getText());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wprowadzono nieprawidłową wartość. Proszę wprowadzić liczbę.");
            return;
        }

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) view.getTable().getRowSorter();
        BigDecimal finalMaxOrderValue = maxOrderValue;
        BigDecimal finalMinOrderValue = minOrderValue;
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean dateInRange = true;
                if (startDate != null && endDate != null) {
                    try {
                        Date orderDate = view.getDataFormatter().parse(entry.getStringValue(1));
                        dateInRange = !orderDate.before(startDate) && !orderDate.after(endDate);
                    } catch (ParseException ex) {
                        return false;
                    }
                }

                String orderId = entry.getStringValue(0);
                Order foundOrder = findById(Integer.parseInt(orderId));
                boolean customerMatches = (selectedCustomer == null || foundOrder.getClient().equals(selectedCustomer));

                boolean orderValueMatches = true;
                if (finalMinOrderValue != null || finalMaxOrderValue != null) {
                    BigDecimal orderValue = new BigDecimal(entry.getStringValue(2));
                    if (finalMinOrderValue != null && orderValue.compareTo(finalMinOrderValue) < 0) {
                        orderValueMatches = false;
                    }
                    if (finalMaxOrderValue != null && orderValue.compareTo(finalMaxOrderValue) > 0) {
                        orderValueMatches = false;
                    }
                }

                return dateInRange && customerMatches && orderValueMatches;
            }
        });
    }

    private void resetFilter() {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) view.getTable().getRowSorter();
        sorter.setRowFilter(null);
        view.getTable().clearSelection();
        view.getStartDateField().setText("");
        view.getEndDateField().setText("");
        view.getMinOrderValueField().setText("");
        view.getMaxOrderValueField().setText("");
    }
}
