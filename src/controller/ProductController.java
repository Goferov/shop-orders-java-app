package controller;

import model.Dimensions;
import model.Product;
import util.ValidatorUtil;
import view.product.ProductFormView;
import view.product.ProductView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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



    @Override
    protected void showDetails(Product element) {
        if(element != null) {
            StringBuilder details = new StringBuilder();
            details.append("Nazwa: ").append(element.getName()).append("\n\n");
            details.append("Opis: \n").append(element.getDescription()).append("\n\n");
            details.append("SKU: ").append(element.getSku()).append("\n\n");
            details.append("Netto: ").append(element.getNetPrice()).append(" zł\n\n");
            details.append("Podatek: ").append(element.getTax()).append("%\n\n");
            details.append("Brutto:\n").append(element.getGrossPrice()).append(" zł\n\n");
            details.append("Waga: ");
            if(element.getWeight() != null) {
                details.append(element.getWeight()).append(" kg\n\n");
            }
            else {
                details.append("Brak\n\n");
            }

            details.append("Wymiary:\n");
            if(element.getDimensions() != null) {
                details
                        .append("Długość: ").append(element.getDimensions().getLength()).append(" cm\n")
                        .append("Szerokość: ").append(element.getDimensions().getWidth()).append(" cm\n")
                        .append("Wysokość: ").append(element.getDimensions().getHeight()).append(" cm\n");
            }
            else {
                details.append("Brak\n");
            }

            JOptionPane.showMessageDialog(view, details, "Szczegóły produktu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected Product create() {
        BigDecimal netPrice = new BigDecimal(form.getNetPriceField().getText());
        int tax = Integer.parseInt(form.getTaxField().getText());
        BigDecimal grossPrice = netPrice.add(
                netPrice.multiply(BigDecimal.valueOf(tax).divide(new BigDecimal("100")))
        );
        grossPrice = grossPrice.setScale(2, RoundingMode.HALF_UP);

        Dimensions dimensions = createDimensions(form.getLengthField().getText(), form.getWidthField().getText(), form.getHeightField().getText());

        return new Product(
                generateNextId(),
                form.getNameField().getText(),
                form.getDescriptionArea().getText(),
                form.getSkuField().getText(),
                netPrice,
                grossPrice,
                tax,
                dimensions,
                ValidatorUtil.validateTextField(form.getWeightField().getText()) ? Double.parseDouble(form.getWeightField().getText()) : null

        );
    }



    @Override
    protected void validateFormFields() {

        validateAndColorField(form.getNameField(), "Nazwa produktu jest wymagana.\n");
        validateAndColorField(form.getSkuField(), "SKU jest wymagane.\n");

        validatePriceField();
        validateTaxField();

        int dimensionsValidationResult = ValidatorUtil.validateDimensions(form.getWidthField().getText(), form.getHeightField().getText(), form.getLengthField().getText());
        validateDimensionsFields(dimensionsValidationResult);

        int weightValidationResult = ValidatorUtil.validateWeight(form.getWeightField().getText());
        validateWeightField(weightValidationResult);
    }

    private void search(String str) {
        if (str.isEmpty()) {
            view.getSorter().setRowFilter(null);
        } else {
            view.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + str, 1));
        }
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

    private void validatePriceField() {
        try {
            BigDecimal price = new BigDecimal(form.getNetPriceField().getText());
            if (!ValidatorUtil.validatePrice(price)) {
                errorMsg.append("Cena musi być większa niż 0 i nie może mieć więcej niż dwie cyfry po przecinku.\n");
                form.getNetPriceField().setBackground(Color.PINK);
            } else {
                form.getNetPriceField().setBackground(Color.WHITE);
            }
        } catch (NumberFormatException e) {
            errorMsg.append("Cena musi być liczbą.\n");
            form.getNetPriceField().setBackground(Color.PINK);
        }
    }

    private void validateTaxField() {
        try {
            int tax = Integer.parseInt(form.getTaxField().getText());
            if (!ValidatorUtil.validateIntRange(tax, 0, 100)) {
                errorMsg.append("Podatek powinien być z zakresu od 0 do 100.\n");
                form.getTaxField().setBackground(Color.PINK);
            } else {
                form.getTaxField().setBackground(Color.WHITE);
            }
        } catch (NumberFormatException e) {
            errorMsg.append("Podatek powinien być liczbą.\n");
            form.getTaxField().setBackground(Color.PINK);
        }
    }

    private void validateDimensionsFields(int validationResult) {
        switch (validationResult) {
            case 0:
                errorMsg.append("Wymiar jest mniejszy od 0.\n");
                form.getWidthField().setBackground(Color.PINK);
                form.getHeightField().setBackground(Color.PINK);
                form.getLengthField().setBackground(Color.PINK);
                break;
            case 1:
                errorMsg.append("Nie wszystkie wymiary są podane.\n");
                form.getWidthField().setBackground(Color.PINK);
                form.getHeightField().setBackground(Color.PINK);
                form.getLengthField().setBackground(Color.PINK);
                break;
            case 2:
                errorMsg.append("Wymiary powinny być liczbą całkowitą.\n");
                form.getWidthField().setBackground(Color.PINK);
                form.getHeightField().setBackground(Color.PINK);
                form.getLengthField().setBackground(Color.PINK);
                break;
            default:
                form.getWidthField().setBackground(Color.WHITE);
                form.getHeightField().setBackground(Color.WHITE);
                form.getLengthField().setBackground(Color.WHITE);
        }
    }

    private void validateWeightField(int validationResult) {
        switch (validationResult) {
            case 1:
                errorMsg.append("Waga jest mniejsza lub równa 0.\n");
                form.getWeightField().setBackground(Color.PINK);
                break;
            case 2:
                errorMsg.append("Waga nie jest liczbą.\n");
                form.getWeightField().setBackground(Color.PINK);
                break;
            default:
                form.getWeightField().setBackground(Color.WHITE);
        }
    }
}
