package controller;

import model.Address;
import model.Customer;
import model.Order;
import util.DateTimeUtil;
import util.FileUtil;
import util.ValidatorUtil;
import view.customer.CustomerFormView;
import view.customer.CustomerView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerController extends AbstractController<Customer, CustomerView, CustomerFormView> {
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerController(CustomerView view, CustomerFormView form) {
        super(view, form, CUSTOMER_FILE);
        form.showDeliveryPanelYes(e -> setDeliveryAddressFields(true));
        form.showDeliveryPanelNo(e -> setDeliveryAddressFields(false));
        view.addActionToFilterButton(e -> search());
        view.addActionToResetButton(e -> resetFilter());
    }



    @Override
    protected void showDetails(Customer element) {
        if (element != null) {
            JTextArea textArea = new JTextArea(10, 30);
            textArea.setText(createCustomerDetailsText(element));
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(view, scrollPane, "Szczegóły Klienta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String createCustomerDetailsText(Customer element) {
        StringBuilder details = new StringBuilder();
        details.append("Imię: ").append(element.getName()).append("\n");
        details.append("Nazwisko: ").append(element.getLastname()).append("\n");
        details.append("Firma: ").append(element.getCompany()).append("\n");
        details.append("NIP: ").append(element.getNip()).append("\n");
        details.append("Adres:\n").append(element.getAddress()).append("\n");
        details.append("Adres dostawy:\n").append(
                element.getDeliveryAddress() != null ? element.getDeliveryAddress() : "Taki sam jak powyżej"
        ).append("\n");

        return details.toString();
    }

    @Override
    protected Customer create() {
        Address customerAddress = new Address(
                form.getStreetField().getText(),
                form.getHouseNumberField().getText(),
                form.getApartmentNumberField().getText(),
                form.getCityField().getText(),
                form.getPostalCodeField().getText(),
                form.getStateField().getText(),
                form.getCountryField().getText()
        );
        Address deliveryAddress = null;
        if (form.getdeliveryAddressYes().isSelected()) {
            deliveryAddress = new Address(
                    form.getDeliveryStreetField().getText(),
                    form.getDeliveryHouseNumberField().getText(),
                    form.getDeliveryApartmentNumberField().getText(),
                    form.getDeliveryCityField().getText(),
                    form.getDeliveryPostalCodeField().getText(),
                    form.getDeliveryStateField().getText(),
                    form.getDeliveryCountryField().getText()
            );
        }

        return new Customer(
                generateNextId(),
                form.getNameField().getText(),
                form.getLastNameField().getText(),
                form.getCompanyField().getText(),
                form.getNipField().getText(),
                customerAddress,
                deliveryAddress
        );
    }

    @Override
    protected void validateFormFields() {
        validateAndColorField(form.getNameField(), "Imię jest wymagane.\n");
        validateAndColorField(form.getLastNameField(), "Nazwisko jest wymagane.\n");

        if (!ValidatorUtil.validateTextField(form.getCompanyField().getText()) && !form.getNipField().getText().isEmpty()) {
            errorMsg.append("Nazwa firmy jest wymagana, gdy podany jest NIP.\n");
            form.getCompanyField().setBackground(Color.PINK);
        }
        else {
            form.getCompanyField().setBackground(Color.WHITE);
        }

        if(!ValidatorUtil.validateNIP(form.getNipField().getText())) {
            errorMsg.append("NIP jest niepoprawny.\n");
        }
        else {
            form.getNipField().setBackground(Color.WHITE);
        }

        if (!ValidatorUtil.validateTextField(form.getNipField().getText()) && !form.getCompanyField().getText().isEmpty()) {
            errorMsg.append("NIP jest wymagany, gdy podana jest nazwa firmy.\n");
            form.getNipField().setBackground(Color.PINK);
        }
        else {
            form.getNipField().setBackground(Color.WHITE);
        }



        validateAndColorField(form.getStreetField(), "Nazwa ulicy jest wymagana.\n");
        validateAndColorField(form.getHouseNumberField(), "Numer budynku jest wymagany.\n");
        validateAndColorField(form.getCityField(), "Miejscowość jest wymagana.\n");
        validateAndColorField(form.getPostalCodeField(), "Kod pocztowy jest niepoprawny.\n");

        if(!ValidatorUtil.validatePostalCode(form.getPostalCodeField().getText())) {
            errorMsg.append("Kod pocztowy jest niepoprawny.\n");
            form.getPostalCodeField().setBackground(Color.PINK);
        }
        else {
            form.getPostalCodeField().setBackground(Color.WHITE);
        }

        validateAndColorField(form.getStateField(), "Województwo jest wymagane.\n");
        validateAndColorField(form.getCountryField(), "Kraj jest wymagany.\n");


        if (form.getdeliveryAddressYes().isSelected()) {
            validateAndColorField(form.getDeliveryStreetField(), "Adres dostawy - Nazwa ulicy jest wymagana.\n");
            validateAndColorField(form.getDeliveryHouseNumberField(), "Adres dostawy - Numer budynku jest wymagany.\n");
            validateAndColorField(form.getDeliveryCityField(), "Adres dostawy - Miejscowość jest wymagana.\n");

            if(!ValidatorUtil.validatePostalCode(form.getDeliveryPostalCodeField().getText())) {
                errorMsg.append("Adres dostawy - Kod pocztowy jest niepoprawny.\n");
                form.getDeliveryPostalCodeField().setBackground(Color.PINK);
            }
            else {
                form.getDeliveryPostalCodeField().setBackground(Color.WHITE);
            }

            validateAndColorField(form.getDeliveryStateField(), "Adres dostawy - Województwo jest wymagane.\n");
            validateAndColorField(form.getDeliveryCountryField(), "Adres dostawy - Kraj jest wymagany.\n");
        }
    }

    private void setDeliveryAddressFields(boolean enable) {
        form.setDeliveryAddressFieldsEnabled(enable);
        if(!enable) {
            resetDeliveryAddressFieldsColor();
        }
    }

    private List<Order> getOrders() {
        return FileUtil.loadFromFile("orders.dat");
    }


    private void search() {
        String startDateString = view.getStartDateField().getText();
        String endDateString = view.getEndDateField().getText();
        Date startDate = DateTimeUtil.parseDate(startDateString);
        Date endDate = DateTimeUtil.parseDate(endDateString);

        BigDecimal minOrderValue = null;

        try {
            if (!view.getMinOrderValueField().getText().isEmpty()) {
                minOrderValue = new BigDecimal(view.getMinOrderValueField().getText());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wprowadzono nieprawidłową wartość. Proszę wprowadzić liczbę.");
            return;
        }
        boolean startFilter = (startDate != null || endDate != null || minOrderValue != null);
        if (!startFilter) {
            return ;
        }

        Map<Customer, BigDecimal> customerOrderTotalMap = getDataList().stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> BigDecimal.ZERO
                ));

        Map<Customer, BigDecimal> ordersMap = getOrders().stream()
                .filter(order -> (startDate == null || !order.getDate().before(startDate)) &&
                        (endDate == null || !order.getDate().after(endDate)))
                .collect(Collectors.groupingBy(
                        Order::getClient,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Order::getOrderTotalPrice,
                                BigDecimal::add
                        )
                ));

        ordersMap.forEach((customer, totalValue) ->
                customerOrderTotalMap.merge(customer, totalValue, BigDecimal::add));

        BigDecimal finalMinOrderValue = minOrderValue;
        Set<Customer> customersMeetingCriteria = ordersMap.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0) // Dodatkowe sprawdzenie, czy klient złożył jakiekolwiek zamówienia
                .filter(entry -> (finalMinOrderValue == null || entry.getValue().compareTo(finalMinOrderValue) >= 0))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) view.getTable().getModel());
        view.getTable().setRowSorter(sorter);

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {

                String clientId = entry.getStringValue(0);
                Customer clientInRow = findById(Integer.parseInt(clientId));
                updateTableWithOrderSum(customerOrderTotalMap);

                return customersMeetingCriteria.contains(clientInRow);
            }
        });
    }

    private void updateTableWithOrderSum(Map<Customer, BigDecimal> customerMap) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

        if (model.findColumn("Suma Zamówień") == -1) {
            model.addColumn("Suma Zamówień");
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Integer clientId = Integer.parseInt(model.getValueAt(i, 0).toString());

            Customer customer = findById(clientId);
            BigDecimal orderSum = customerMap.getOrDefault(customer, BigDecimal.ZERO);

            model.setValueAt(orderSum, i, model.getColumnCount() - 1);
        }
        int newColumnIndex = model.getColumnCount() - 1;
        setSorterToBigDecimalValue(newColumnIndex);
    }

    private void removeOrderSumColumn() {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

        int orderSumColumnIndex = model.findColumn("Suma Zamówień");

        if (orderSumColumnIndex != -1) {
            TableColumnModel columnModel = view.getTable().getColumnModel();
            TableColumn orderSumColumn = columnModel.getColumn(orderSumColumnIndex);
            view.getTable().removeColumn(orderSumColumn);
            model.setColumnCount(model.getColumnCount() - 1);
        }
    }

    private void resetFilter() {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) view.getTable().getRowSorter();
        sorter.setRowFilter(null);
        view.getTable().clearSelection();
        view.getStartDateField().setText("");
        view.getEndDateField().setText("");
        view.getMinOrderValueField().setText("");
        removeOrderSumColumn();
    }

    private void resetDeliveryAddressFieldsColor() {
        form.getDeliveryStreetField().setBackground(Color.WHITE);
        form.getDeliveryHouseNumberField().setBackground(Color.WHITE);
        form.getDeliveryApartmentNumberField().setBackground(Color.WHITE);
        form.getDeliveryCityField().setBackground(Color.WHITE);
        form.getDeliveryPostalCodeField().setBackground(Color.WHITE);
        form.getDeliveryStateField().setBackground(Color.WHITE);
        form.getDeliveryCountryField().setBackground(Color.WHITE);
    }

}
