package controller;


import view.customer.CustomerFormView;
import view.customer.CustomerView;
import view.MainView;
import view.product.ProductView;

public class MainController {
    private MainView mainView;
    private CustomerController customerController;
    private CustomerView customerView;
    private ProductView productView;
    private CustomerFormView customerForm;

    public MainController() {
        mainView = new MainView();

        customerView = new CustomerView();
        customerForm = new CustomerFormView(mainView);
        customerController = new CustomerController(customerView, customerForm);
        mainView.addTab("Klienci", customerView);

        productView = new ProductView();
         mainView.addTab("Produkty", productView);
        // mainView.addTab("Zamówienia", new CustomerView());

        mainView.setVisible(true);
    }

}