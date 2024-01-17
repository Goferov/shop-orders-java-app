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

    public JTextField getNameField() {
        return nameField;
    }

    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }

    public JTextField getSkuField() {
        return skuField;
    }

    public JTextField getNetPriceField() {
        return netPriceField;
    }

    public JTextField getTaxField() {
        return taxField;
    }

    public JTextField getLengthField() {
        return lengthField;
    }

    public JTextField getWidthField() {
        return widthField;
    }

    public JTextField getHeightField() {
        return heightField;
    }

    public JTextField getWeightField() {
        return weightField;
    }

    @Override
    protected void addFieldsToForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = 1.0;



        addField(formPanel, "Nazwa:", nameField, gbc);
        descriptionScrollPane.setPreferredSize(new Dimension(200, 100)); // szerokość x wysokość

        addField(formPanel, "Opis (opcjonalnie):", descriptionScrollPane, gbc); // Specjalne traktowanie dla JTextArea z JScrollPane

        addField(formPanel, "SKU:", skuField, gbc);

        addField(formPanel, "Cena NETTO:", netPriceField, gbc);

        addField(formPanel, "Podatek %:", taxField, gbc);

        addField(formPanel, "Długość cm (opcjonalnie):", lengthField, gbc);

        addField(formPanel, "Szerokość cm (opcjonalnie):", widthField, gbc);

        addField(formPanel, "Wysokość cm (opcjonalnie):", heightField, gbc);

        addField(formPanel, "Waga kg (opcjonalnie):", weightField, gbc);
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
