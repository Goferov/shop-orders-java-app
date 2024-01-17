package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractFormView extends JDialog {
    private final JButton submitBtn = new JButton("Wyślij");
    private final JButton cancelBtn = new JButton("Anuluj");

    public AbstractFormView(Frame parent, String title) {
        super(parent, title, true);
        getContentPane().setLayout(new BorderLayout());
        addBtnsToForm();
    }

    public void addBtnsToForm() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }


    protected void addField(JPanel panel, String label, Component field, GridBagConstraints gbc) {
        panel.add(new JLabel(label), gbc);
        gbc.gridx++;
        if (field instanceof JTextField) {
            ((JTextField) field).setPreferredSize(new Dimension(200, 20));
        }
        panel.add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    protected abstract void addFieldsToForm();
    public abstract void clearFormFields();

    public void submitForm(ActionListener actionListener) {
        submitBtn.addActionListener(actionListener);
    }

    public void cancelForm(ActionListener actionListener) {
        cancelBtn.addActionListener(actionListener);
    }
}
