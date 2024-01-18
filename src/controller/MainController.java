package controller;


import view.customer.CustomerFormView;
import view.customer.CustomerView;
import view.MainView;
import view.order.OrderFormView;
import view.order.OrderView;
import view.product.ProductFormView;
import view.product.ProductView;

public class MainController {

    public MainController() {
        MainView mainView = new MainView();

        CustomerView customerView = new CustomerView();
        CustomerFormView customerForm = new CustomerFormView(mainView);
        CustomerController customerController = new CustomerController(customerView, customerForm);
        mainView.addTab("Klienci", customerView);

        ProductView productView = new ProductView();
        ProductFormView productFormView = new ProductFormView(mainView);
        ProductController productController = new ProductController(productView, productFormView);
        mainView.addTab("Produkty", productView);

        OrderView orderView = new OrderView();
        OrderFormView orderFormView = new OrderFormView(mainView);
        OrderController orderController = new OrderController(orderView, orderFormView);
        mainView.addTab("Zamówienia", orderView);

        mainView.setVisible(true);
    }

}