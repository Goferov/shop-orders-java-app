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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderController extends AbstractController<Order, OrderView, OrderFormView> {

    private Customer selectedCustomer;
    private Customer latestCustomer;
    private Date orderDate;
    private int quantity;
    private int discount;
    private List<ItemsList> itemsList = new ArrayList<>();

    protected OrderController(OrderView view, OrderFormView orderFormView) {
        super(view, orderFormView, "orders.dat");
//        form.addActionToCustomerList(e -> updateDeliveryAddressFields());
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
    }

    @Override
    protected void showDetails(Order element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();

            details.append("Identyfikator: \n").append(element.getId()).append("\n\n");
            details.append("Data: \n").append(DateTimeUtil.showDate(element.getDate())).append("\n\n");
            details.append("Klient: \n").append(element.getClient()).append("\n\n");
            details.append("Produkty: \n");
            details.append(String.format("%-10s %-30s %-10s %-10s %-15s %-15s\n", "ID", "Nazwa", "Ilość", "Rabat", "Netto", "Brutto"));
            details.append("----------------------------------------------------------------------------------------\n");
            for(ItemsList item : element.getItemsList()) {
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

            JOptionPane.showMessageDialog(view, details, "Szczegóły zamówienia", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Order create() {
        return new Order(
                generateNextId(),
                orderDate,
                itemsList,
                selectedCustomer,
                createAddress()
        );
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

    @Override
    protected void validateFormFields() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            orderDate = sdf.parse(form.getOrderDateField());
        } catch (Exception e) {
            errorMsg.append("Data powinna być zapisana w formacie yyyy-mm-dd.\n");
        }

        if(selectedCustomer == null) {
            errorMsg.append("Nie wybrano klienta.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getDeliveryStreetField().getText())) {
            errorMsg.append("Adres dostawy - nazwa ulicy jest wymagana.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getDeliveryHouseNumberField().getText())) {
            errorMsg.append("Adres dostawy - numer budynku jest wymagany.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getDeliveryCityField().getText())) {
            errorMsg.append("Adres dostawy - miejscowość jest wymagana.\n");
        }

        if (!ValidatorUtil.validatePostalCode(form.getDeliveryPostalCodeField().getText())) {
            errorMsg.append("Adres dostawy - kod pocztowy jest niepoprawny.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getDeliveryStateField().getText())) {
            errorMsg.append("Adres dostawy - województwo jest wymagane.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getDeliveryCountryField().getText())) {
            errorMsg.append("Adres dostawy - kraj jest wymagany.\n");
        }

        validateAndProcessProductTable();
    }

    private void validateAndProcessProductTable() {
        DefaultTableModel tableModel = form.getproductTableModel();
        itemsList.clear();
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

    private void addProductsToComboBox() {
        form.getProductComboBox().removeAllItems();
        List<Product> prods = getProducts();
        for (Product product : prods) {
            form.getProductComboBox().addItem(product);
        }
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

    private BigDecimal priceAfterDiscount(BigDecimal price, int discount) {
        BigDecimal rabatDecimal = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
        return price.multiply(BigDecimal.valueOf(discount)).multiply(BigDecimal.ONE.subtract(rabatDecimal));
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

    private PopupMenuListener customerComboBoxAction() {
        return new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                addCustomersToFormComboBox(form.getCustomerComboBox());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                updateDeliveryAddressFields();
            }

            public void popupMenuCanceled(PopupMenuEvent e) {}
        };
    }

    private void search() {
        Date startDate = (Date) view.getStartDateSpinner().getValue();
        Date endDate = (Date) view.getEndDateSpinner().getValue();

        Customer selectedCustomer = (Customer) view.getCustomerComboBox().getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) view.getTable().getRowSorter();
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean dateInRange = true;  // Domyślnie prawdziwe, jeśli filtr daty nie jest ustawiony
                if (startDate != null && endDate != null) {
                    try {
                        Date orderDate = view.getDataFormatter().parse(entry.getStringValue(1));
                        dateInRange = !orderDate.before(startDate) && !orderDate.after(endDate);
                    } catch (ParseException ex) {
                        return false;  // Jeśli nie można sparsować daty, wiersz nie zostanie uwzględniony
                    }
                }

                String orderId = entry.getStringValue(0);
                Order findedOrder = findById(Integer.parseInt(orderId));
                boolean customerMatches = (selectedCustomer == null || findedOrder.getClient().equals(selectedCustomer));

                return dateInRange && customerMatches;
            }
        });
    }

    private Order getOrderById() {
        return null;
    }

    private void resetFilter() {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) view.getTable().getRowSorter();
        sorter.setRowFilter(null);  // Usunięcie wszelkich filtrów
        view.getTable().clearSelection();  // Opcjonalnie, czyści zaznaczenie w tabeli
        form.getCustomerComboBox().setSelectedIndex(-1);
    }
}
