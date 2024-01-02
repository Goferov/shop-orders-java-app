package controller;

import model.Product;
import view.product.ProductFormView;
import view.product.ProductView;

public class ProductController extends AbstractController <Product, ProductView, ProductFormView>{

    public ProductController(ProductView view, ProductFormView form) {
        super(view, form, "products.dat");
    }

    @Override
    protected void showDetails(Product element) {

    }

    @Override
    protected Product create() {
        return null;
    }

    @Override
    protected boolean validateFormFields() {
        return false;
    }
}
