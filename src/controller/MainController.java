package controller;


import view.CustomerFormView;
import view.CustomerView;
import view.MainView;

public class MainController {
    private MainView mainView;
    private CustomerController customerController;
    private CustomerView customerView;
    private CustomerFormView customerForm;

    public MainController() {
        mainView = new MainView();
        customerView = new CustomerView();
        customerForm = new CustomerFormView(mainView);
        customerController = new CustomerController(customerView, customerForm);
        mainView.addTab("Klienci", customerView);
        // mainView.addTab("Produkty", new CustomerView());
        // mainView.addTab("Zamówienia", new CustomerView());

        mainView.setVisible(true);
    }

}