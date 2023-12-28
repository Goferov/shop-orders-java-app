import gui.CustomerGui;
import javax.swing.*;

public class Main  {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    public Main() {
        initializeUI();
    }
    public void display() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.display();
    }

    private void initializeUI() {
        frame = new JFrame("System Zarządzania");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Klienci", new CustomerGui());
        tabbedPane.addTab("Produkty", new CustomerGui());
        tabbedPane.addTab("Zamówienia", new CustomerGui());

        frame.add(tabbedPane);
    }
}