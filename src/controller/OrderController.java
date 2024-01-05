package controller;

import model.Address;
import model.Customer;
import model.Order;
import model.Product;
import util.FileUtil;
import util.ValidatorUtil;
import view.order.OrderFormView;
import view.order.OrderView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderController extends AbstractController<Order, OrderView, OrderFormView> {

    private Customer selectedCustomer;
    private Date orderDate;

    protected OrderController(OrderView view, OrderFormView orderFormView) {
        super(view, orderFormView, "orders.dat");
        form.addActionToCustomerList(e -> updateDeliveryAddressFields());
        form.addActionToRemoveButton(e -> removeProductFromOrderList());
        addCustomersToComboBox();

    }

    @Override
    protected void showDetails(Order element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();
            details.append("Identyfikator: \n").append(element.getId()).append("\n\n");
            details.append("Data: \n").append(element.getDate()).append("\n\n");
            details.append("Klient: \n").append(element.getClient()).append("\n\n");
            details.append("Produkty: \n").append("tu będzie lista produktów").append("\n\n");
            details.append("Cena całkowita: \n").append(element.getOrderPrice()).append(" zł\n\n");
            details.append("Adres dostawy:\n").append(element.getDeliveryAddress()).append("\n\n");

            JOptionPane.showMessageDialog(view, details, "Szczegóły zamówienia", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Order create() {
        return new Order(
                generateNextId(),
                orderDate,
                null,
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
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            try {
                // Pobierz wartości z wiersza
                String productName = (String) tableModel.getValueAt(row, 1);
                BigDecimal ilosc = new BigDecimal(tableModel.getValueAt(row, 3).toString());
                BigDecimal rabat = new BigDecimal(tableModel.getValueAt(row, 4).toString());

                // Walidacja wartości
                if (ilosc.compareTo(BigDecimal.ZERO) <= 0) {
                    errorMsg.append("Ilość musi być większa od 0: " + productName + ".\n");
                    continue;  // Kontynuuj z następnym wierszem
                }

                if (rabat.compareTo(BigDecimal.ZERO) < 0) {
                    errorMsg.append("Rabat musi być większy od 0: " + productName + ".\n");
                    continue;  // Kontynuuj z następnym wierszem
                }

            } catch (NumberFormatException e) {
                errorMsg.append("Wartości muszą być liczbami dla produktu: " + tableModel.getValueAt(row, 1) + ".\n");
            } catch (NullPointerException e) {
                errorMsg.append("Wszystkie pola muszą być wypełnione dla produktu: " + tableModel.getValueAt(row, 1) + ".\n");
            }
        }
    }

    private List<Customer> getCustomers() {
       return FileUtil.loadFromFile("customers.dat");
    }

    private List<Product> getProducts() {
        return FileUtil.loadFromFile("products.dat");
    }

    private void addCustomersToComboBox() {
        List<Customer> dataList = getCustomers();
        for(Customer customer : dataList) {
            form.getCustomerComboBox().addItem(customer);
        }
        form.getCustomerComboBox().setSelectedIndex(-1);
    }

    private void updateDeliveryAddressFields() {
        if (!form.getCustomerComboBox().isFocusOwner()) {
            return;
        }

        selectedCustomer = (Customer) form.getCustomerComboBox().getSelectedItem();
        if (selectedCustomer != null) {
            System.out.println(selectedCustomer);
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

    private void removeProductFromOrderList() {
//        Integer selectedElement = getSelectedElementFromProductList();
//        if (selectedElement != null) {
//            for (int i = 0; i < form.getproductTableModel().getRowCount(); i++) {
//                if (form.getproductTableModel().getValueAt(i, 0).equals(selectedElement)) {
//                    form.getproductTableModel().removeRow(i);
////                    form.getProductComboBox().
//                    break;
//                }
//            }
//        }
    }
}
