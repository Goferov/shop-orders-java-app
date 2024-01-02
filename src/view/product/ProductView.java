package view.product;

import model.Product;
import view.AbstractView;

public class ProductView extends AbstractView {
    public ProductView() {
        super(new String[]{"ID", "Nazwa", "SKU", "Cena Netto", "Cena Brutto"});
    }

    @Override
    public void addToView(Object o) {
        Product product  = (Product) o;
        tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getSku(), product.getNetPrice(), product.getGrossPrice()});
    }
}
