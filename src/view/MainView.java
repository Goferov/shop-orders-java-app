package view;

import javax.swing.*;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane;

    public MainView() {
        setTitle("System zarządzania zamówieniami");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        add(tabbedPane);
    }

    public void addTab(String title, JPanel panel) {
        tabbedPane.addTab(title, panel);
    }
}
