package controller;


import view.customer.CustomerFormView;
import view.customer.CustomerView;
import view.MainView;
import view.order.OrderFormView;
import view.order.OrderView;
import view.product.ProductFormView;
import view.product.ProductView;

public class MainController {
    private MainView mainView;
    private CustomerController customerController;
    private ProductController productController;
    private OrderController orderController;
    private CustomerView customerView;
    private ProductView productView;
    private OrderView orderView;
    private CustomerFormView customerForm;
    private ProductFormView productFormView;
    private OrderFormView orderFormView;

    public MainController() {
        mainView = new MainView();

        customerView = new CustomerView();
        customerForm = new CustomerFormView(mainView);
        customerController = new CustomerController(customerView, customerForm);
        mainView.addTab("Klienci", customerView);

        productView = new ProductView();
        productFormView = new ProductFormView(mainView);
        productController = new ProductController(productView, productFormView);
         mainView.addTab("Produkty", productView);

        orderView = new OrderView();
        orderFormView = new OrderFormView(mainView);
        orderController = new OrderController(orderView, orderFormView);
        mainView.addTab("Zamówienia", orderView);

        mainView.setVisible(true);
    }

}