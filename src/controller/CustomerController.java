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
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerController extends AbstractController<Customer, CustomerView, CustomerFormView> {
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerController(CustomerView view, CustomerFormView form) {
        super(view, form, CUSTOMER_FILE);
        form.showDeliveryPanelYes(e -> toggleDeliveryAddressFields(true));
        form.showDeliveryPanelNo(e -> toggleDeliveryAddressFields(false));
        view.addActionToFilterButton(e -> search());
        view.addActionToResetButton(e -> resetFilter());
    }


    private void toggleDeliveryAddressFields(boolean show) {
        form.getDeliveryAddressPanel().setVisible(show);
        form.pack();
    }

    private List<Order> getOrders() {
        return FileUtil.loadFromFile("orders.dat");
    }

    @Override
    protected void showDetails(Customer element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();
            details.append("Imię: ").append(element.getName()).append("\n");
            details.append("Nazwisko: ").append(element.getLastname()).append("\n");
            details.append("Firma: ").append(element.getCompany()).append("\n");
            details.append("NIP: ").append(element.getNip()).append("\n\n");
            details.append("Adres:\n").append(element.getAddress()).append("\n\n");
            details.append("Adres dostawy:\n").append(
                    element.getDeliveryAddress() != null ? element.getDeliveryAddress() : "Taki sam jak powyżej"
            ).append("\n\n");

            JOptionPane.showMessageDialog(view, details, "Szczegóły Klienta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Customer create() {
        Address customerAddress = new Address(
                form.getStreetField(),
                form.getHouseNumberField(),
                form.getApartmentNumberField(),
                form.getCityField(),
                form.getPostalCodeField(),
                form.getStateField(),
                form.getCountryField()
        );
        Address deliveryAddress = null;
        if (form.getdeliveryAddressYes().isSelected()) {
            deliveryAddress = new Address(
                    form.getDeliveryStreetField(),
                    form.getDeliveryHouseNumberField(),
                    form.getDeliveryApartmentNumberField(),
                    form.getDeliveryCityField(),
                    form.getDeliveryPostalCodeField(),
                    form.getDeliveryStateField(),
                    form.getDeliveryCountryField()
            );
        }

        return new Customer(
                generateNextId(),
                form.getNameField(),
                form.getLastNameField(),
                form.getCompanyField(),
                form.getNipField(),
                customerAddress,
                deliveryAddress
        );
    }

    protected void validateFormFields() {

        if(!ValidatorUtil.validateTextField(form.getNameField())) {
            errorMsg.append("Imię jest wymagane.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getLastNameField())) {
            errorMsg.append("Nazwisko jest wymagane.\n");
        }

        if (!ValidatorUtil.validateTextField(form.getCompanyField()) && !form.getNipField().isEmpty()) {
            errorMsg.append("Nazwa firmy jest wymagana, gdy podany jest NIP.\n");
        }

        if (!ValidatorUtil.validateNIP(form.getNipField()) && !form.getCompanyField().isEmpty()) {
            errorMsg.append("NIP jest wymagany, gdy podana jest nazwa firmy.\n");
        }

        if(!ValidatorUtil.validateNIP(form.getNipField())) {
            errorMsg.append("NIP jest niepoprawny.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getStreetField())) {
            errorMsg.append("Nazwa ulicy jest wymagana.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getHouseNumberField())) {
            errorMsg.append("Numer budynku jest wymagany.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getCityField())) {
            errorMsg.append("Miejscowość jest wymagana.\n");
        }

        if(!ValidatorUtil.validatePostalCode(form.getPostalCodeField())) {
            errorMsg.append("Kod pocztowy jest niepoprawny.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getStateField())) {
            errorMsg.append("Województwo jest niepoprawne.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getCountryField())) {
            errorMsg.append("Kraj jest wymagany.\n");
        }

        if (form.getdeliveryAddressYes().isSelected()) {
            if (!ValidatorUtil.validateTextField(form.getDeliveryStreetField())) {
                errorMsg.append("Adres dostawy - nazwa ulicy jest wymagana.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryHouseNumberField())) {
                errorMsg.append("Adres dostawy - numer budynku jest wymagany.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryCityField())) {
                errorMsg.append("Adres dostawy - miejscowość jest wymagana.\n");
            }

            if (!ValidatorUtil.validatePostalCode(form.getDeliveryPostalCodeField())) {
                errorMsg.append("Adres dostawy - kod pocztowy jest niepoprawny.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryStateField())) {
                errorMsg.append("Adres dostawy - województwo jest wymagane.\n");
            }

            if (!ValidatorUtil.validateTextField(form.getDeliveryCountryField())) {
                errorMsg.append("Adres dostawy - kraj jest wymagany.\n");
            }
        }
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
        Map<Customer, BigDecimal> customerOrderTotalMap = getOrders().stream()
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

        BigDecimal finalMinOrderValue = minOrderValue;
        Set<Customer> customersMeetingCriteria = customerOrderTotalMap.entrySet().stream()
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
//        view.getMaxOrderValueField().setText("");
    }

}
