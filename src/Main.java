import gui.MainGui;

import javax.swing.*;

public class Main  {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGui mainGui = new MainGui();
            mainGui.setVisible(true);
        });
    }

}