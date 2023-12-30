package controller;


import view.CustomerView;
import view.MainView;

public class MainController {
    private MainView mainView;
    private CustomerController customerController;
    private CustomerView customerView;

    public MainController() {
        mainView = new MainView();
        customerView = new CustomerView();
        customerController = new CustomerController(customerView);
        mainView.addTab("Klienci", customerView);
        // mainView.addTab("Produkty", new CustomerView());
        // mainView.addTab("Zamówienia", new CustomerView());

        mainView.setVisible(true);
    }

}