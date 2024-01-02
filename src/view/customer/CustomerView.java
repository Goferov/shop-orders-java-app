package view.customer;

import model.Customer;
import view.AbstractView;


public class CustomerView extends AbstractView {
    public CustomerView() {
        super(new String[]{"ID", "Imię", "Nazwisko", "Nazwa firmy", "NIP"});
    }

    @Override
    public void addToView(Object o) {
        Customer customer  = (Customer) o;
        tableModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getLastname(), customer.getCompany(), customer.getNip()});
    }
}
