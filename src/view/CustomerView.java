package view;

import model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {
    private JButton addButton;
    private JButton removeButton;
    private final String[] columnNames = {"ID", "Imię", "Nazwisko"};
    private DefaultTableModel tableModel;
    private JTable table;

    public CustomerView() {
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane listScrollPane = new JScrollPane(table);

        addButton = new JButton("Dodaj");
        removeButton = new JButton("Usuń");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

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

    public void addCustomerToView(Customer customer) {
        tableModel.addRow(new Object[]{customer.getId(), customer.getName(), customer.getLastname()});
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
            return (int) (Integer) tableModel.getValueAt(selectedRow, 0);
        }
        return null; // lub zwrócić znalezionego klienta
    }

}
