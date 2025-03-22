package taskmanagement;

import taskmanagement.gui.TaskManagementGUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagementGUI gui = new TaskManagementGUI();
            gui.setVisible(true);
        });
    }
}