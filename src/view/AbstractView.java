package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;

public abstract class AbstractView extends JPanel {
    protected JButton addButton = new JButton("Dodaj");;
    protected JButton removeButton = new JButton("Usuń");;
    protected JButton filterButton = new JButton("Filtruj");
    protected JButton resetButton = new JButton("Reset");
    protected JTable table;
    public DefaultTableModel tableModel;
    protected TableRowSorter<TableModel> sorter;

    public AbstractView(String[] columnNames) {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == findColumn("Suma Zamówień")) {
                    return BigDecimal.class;
                }
                return super.getColumnClass(column);
            }
        };
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        JScrollPane listScrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
    }

    public void addButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void removeButtonAction(ActionListener action) {
        removeButton.addActionListener(action);
    }

    public void doubleClickAction(MouseAdapter action) {
        table.addMouseListener(action);
    }

    public void addActionToFilterButton(ActionListener action) {
        filterButton.addActionListener(action);
    }

    public void addActionToResetButton(ActionListener action) {
        resetButton.addActionListener(action);
    }

    public abstract void addToView(Object o);

    public void removeFromView(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(id)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public Integer getSelectedElement() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return (Integer) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    public JTable getTable() {
        return table;
    }
}
