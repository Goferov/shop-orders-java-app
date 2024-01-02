package view.customer;

import model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomerView extends JPanel {
    private JButton addButton;
    private JButton removeButton;
    private JButton filterButton;
    private final String[] columnNames = {"ID", "Imię", "Nazwisko", "Nazwa firmy", "NIP"};
    private DefaultTableModel tableModel;
    private JTable table;

    public CustomerView() {
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane listScrollPane = new JScrollPane(table);

        addButton = new JButton("Dodaj");
        removeButton = new JButton("Usuń");
        filterButton = new JButton("Filtruj");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(filterButton);

        setLayout(new BorderLayout());
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Zaznaczanie pojedynczego wiersza
        table.setDefaultEditor(Object.class, null);
    }

    public void setAddButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void setRemoveButtonAction(ActionListener action) {
        removeButton.addActionListener(action);
    }

    public void setFilterButtonAction(ActionListener action) {
        filterButton.addActionListener(action);
    }

    public void setOnDoubleClickAction(MouseAdapter action) {
        table.addMouseListener(action);
    }

    public void addCustomerToView(Customer customer) {
        tableModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getLastname(), customer.getCompany(), customer.getNip()});
    }

    public void removeCustomerFromView(int customerId) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(customerId)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public Integer getSelectedCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return (Integer) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

}
