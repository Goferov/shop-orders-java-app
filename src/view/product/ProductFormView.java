package view.product;

import view.AbstractFormView;

import javax.swing.*;
import java.awt.*;

public class ProductFormView extends AbstractFormView {

    private JTextField nameField = new JTextField();
    private JTextArea descriptionArea = new JTextArea();
    private JTextField skuField = new JTextField();
    private JTextField netPriceField = new JTextField();
    private JTextField taxField = new JTextField();
    private JTextField lengthField = new JTextField();
    private JTextField widthField = new JTextField();
    private JTextField heightField = new JTextField();
    private JTextField weightField = new JTextField();

    public ProductFormView(Frame parent) {
        super(parent, "Wpisz dane nowego produktu");
        addFieldsToForm();
        pack();
        setLocationRelativeTo(parent);
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getDescriptionArea() {
        return descriptionArea.getText();
    }

    public String getSkuField() {
        return skuField.getText();
    }

    public String getNetPriceField() {
        return netPriceField.getText();
    }

    public String getTaxField() {
        return taxField.getText();
    }

    public String getLengthField() {
        return lengthField.getText();
    }

    public String getWidthField() {
        return widthField.getText();
    }

    public String getHeightField() {
        return heightField.getText();
    }

    public String getWeightField() {
        return weightField.getText();
    }

    @Override
    protected void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));

        formPanel.add(new JLabel("Nazwa:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Opis (opcjonalnie):"));
        formPanel.add(descriptionArea);
        formPanel.add(new JLabel("SKU:"));
        formPanel.add(skuField);
        formPanel.add(new JLabel("Cena NETTO:"));
        formPanel.add(netPriceField);
        formPanel.add(new JLabel("Podatek %:"));
        formPanel.add(taxField);
        formPanel.add(new JLabel("Długość cm (opcjonalnie):"));
        formPanel.add(lengthField);
        formPanel.add(new JLabel("Szerokość cm (opcjonalnie):"));
        formPanel.add(widthField);
        formPanel.add(new JLabel("Wysokość cm (opcjonalnie):"));
        formPanel.add(heightField);
        formPanel.add(new JLabel("Waga kg (opcjonalnie):"));
        formPanel.add(weightField);
        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    @Override
    public void clearFormFields() {
        nameField.setText("");
        descriptionArea.setText("");
        skuField.setText("");
        netPriceField.setText("");
        taxField.setText("");
        lengthField.setText("");
        widthField .setText("");
        heightField.setText("");
        weightField.setText("");
    }
}
