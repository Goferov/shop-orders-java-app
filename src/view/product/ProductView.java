package view.product;

import model.Product;
import view.AbstractView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ProductView extends AbstractView {
    public ProductView() {
        super(new String[]{"ID", "Nazwa", "SKU", "Cena Netto", "Cena Brutto"});
        JPanel filterPanel = new JPanel();
        JTextField filterField = new JTextField(15);
        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);

        filterPanel.add(filterField);
        filterPanel.add(new JLabel("Min Cena:"));
        filterPanel.add(minPriceField);
        filterPanel.add(new JLabel("Max Cena:"));
        filterPanel.add(maxPriceField);
        add(filterPanel, BorderLayout.NORTH);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            public void search(String str) {
                if (str.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                search(filterField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(filterField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(filterField.getText());
            }
        });
    }

    @Override
    public void addToView(Object o) {
        Product product  = (Product) o;
        tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getSku(), product.getNetPrice(), product.getGrossPrice()});
    }
}
