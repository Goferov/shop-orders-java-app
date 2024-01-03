package view.product;

import model.Product;
import view.AbstractView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class ProductView extends AbstractView {
    JTextField filterField = new JTextField(15);
    TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());

    public ProductView() {
        super(new String[]{"ID", "Nazwa", "SKU", "Cena Netto", "Cena Brutto"});
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Szukaj po nazwie:"));
        filterPanel.add(filterField);
        add(filterPanel, BorderLayout.NORTH);
        table.setRowSorter(sorter);
    }

    public JTextField getFilterField() {
        return filterField;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    @Override
    public void addToView(Object o) {
        Product product  = (Product) o;
        tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getSku(), product.getNetPrice(), product.getGrossPrice()});
    }

    public void searchAction(DocumentListener listener) {
        filterField.getDocument().addDocumentListener(listener);
    }
}
