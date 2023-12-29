package gui;

import javax.swing.*;

public class MainGui extends JFrame {
    public MainGui() {

        setTitle("System zarządzania zamówieniami");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Klienci", new CustomerGui());
//        tabbedPane.addTab("Produkty", new CustomerGui());
//        tabbedPane.addTab("Zamówienia", new CustomerGui());

        add(tabbedPane);

    }
}
