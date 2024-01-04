package controller;

import model.Dimensions;
import model.Product;
import util.ValidatorUtil;
import view.product.ProductFormView;
import view.product.ProductView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.math.BigDecimal;

public class ProductController extends AbstractController <Product, ProductView, ProductFormView>{

    public ProductController(ProductView view, ProductFormView form) {
        super(view, form, "products.dat");

        view.searchAction(new DocumentListener() {
            public void updateSearch(DocumentEvent e) {
                search(view.getFilterField().getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearch(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearch(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearch(e);
            }
        });

    }

    private void search(String str) {
        if (str.isEmpty()) {
            view.getSorter().setRowFilter(null);
        } else {
            view.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + str, 1));
        }
    }

    @Override
    protected void showDetails(Product element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();
            details.append("Nazwa: ").append(element.getName()).append("\n");
            details.append("Opis: \n").append(element.getDescription()).append("\n");
            details.append("SKU: ").append(element.getSku()).append("\n");
            details.append("Netto: ").append(element.getNetPrice()).append(" zł\n");
            details.append("Podatek: ").append(element.getTax()).append("%\n");
            details.append("Brutto:\n").append(element.getGrossPrice()).append(" zł\n");
            details.append("Waga: ");
            if(element.getWeight() != null) {
                details.append(element.getWeight()).append(" kg\n");
            }
            else {
                details.append("Brak\n");
            }

            details.append("Wymiary:\n");
            if(element.getDimensions() != null) {
                details
                        .append("   Długość: ").append(element.getDimensions().getLength()).append(" cm\n")
                        .append("   Szerokość: ").append(element.getDimensions().getWidth()).append(" cm\n")
                        .append("   Wysokość: ").append(element.getDimensions().getHeight()).append(" cm\n");
            }
            else {
                details.append("Brak\n");
            }

            JOptionPane.showMessageDialog(view, details, "Szczegóły produktu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Product create() {
        BigDecimal netPrice = new BigDecimal(form.getNetPriceField());
        int tax = Integer.parseInt(form.getTaxField());
        BigDecimal grossPrice = netPrice.add(netPrice.multiply( BigDecimal.valueOf(tax).divide(new BigDecimal("100"))));

        Dimensions dimensions = createDimensions(form.getLengthField(), form.getWidthField(), form.getHeightField());

        return new Product(
                generateNextId(),
                form.getNameField(),
                form.getDescriptionArea(),
                form.getSkuField(),
                netPrice,
                grossPrice,
                tax,
                dimensions,
                ValidatorUtil.validateTextField(form.getWeightField()) ? Double.parseDouble(form.getWeightField()) : null

        );
    }

    private Dimensions createDimensions(String lengthStr, String widthStr, String heightStr) {
        if (!lengthStr.isEmpty() && !widthStr.isEmpty() && !heightStr.isEmpty()) {
            double length = Double.parseDouble(lengthStr);
            double width = Double.parseDouble(widthStr);
            double height = Double.parseDouble(heightStr);
            if (length > 0 && width > 0 && height > 0) {
                return new Dimensions(length, width, height);
            }
        }
        return null;
    }

    @Override
    protected void validateFormFields() {

        System.out.println(form.getNameField());
        if(!ValidatorUtil.validateTextField(form.getNameField())) {
            errorMsg.append("Nazwa produktu jest wymagana.\n");
        }

        if(!ValidatorUtil.validateTextField(form.getSkuField())) {
            errorMsg.append("SKU jest wymagane.\n");
        }

        try {
            BigDecimal price = new BigDecimal(form.getNetPriceField());
            if(!ValidatorUtil.validatePrice(price)) {
                errorMsg.append("Cena musi być większa niż 0 i nie może mieć więcej niż dwie cyfry po przecinku.\n");
            }

        } catch (NumberFormatException e) {
            errorMsg.append("Cena musi być liczbą.\n");
        }

        try {
            int tax = Integer.parseInt(form.getTaxField());
            if(!ValidatorUtil.validateIntRange(tax, 0, 100)) {
                errorMsg.append("Podatek powinien być z zakresu od 0 do 100.\n");
            }

        } catch (NumberFormatException e) {
            errorMsg.append("Podatek powinien być liczbą.\n");
        }

        switch (ValidatorUtil.validateDimensions(form.getWidthField(), form.getHeightField(), form.getLengthField())) {
            case 0:
                errorMsg.append("Wymiar jest mniejszy od 0.\n");
            case 1:
                errorMsg.append("Nie wszystkie wymiary są podane.\n");
            case 2:
                errorMsg.append("Wymiary powinny być liczbą całkowitą.\n");
        }

        switch (ValidatorUtil.validateWeight(form.getWeightField())) {
            case 1:
                errorMsg.append("Waga jest mniejsza lub równa 0.\n");
            case 2:
                errorMsg.append("Waga nie jest liczbą.\n");
        }
    }
}
