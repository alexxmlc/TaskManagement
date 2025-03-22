package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.model.Employee;
import taskmanagement.model.Task;

import javax.swing.*;
import java.awt.*;

public class ViewDataPanel extends JPanel {
    private final TasksManagement tasksManagement;
    private JTextArea displayArea;

    public ViewDataPanel(TasksManagement tasksManagement) {
        this.tasksManagement = tasksManagement;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshData());

        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        refreshData();
    }

    void refreshData() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Employees ===\n");
        for(Employee e : tasksManagement.getEmployees()) {
            sb.append("- ").append(e.getName()).append("\n");
            sb.append("  Assigned Tasks:\n");
            for(Task t : e.getAssignedTasks()) {
                sb.append("  • ").append(t.getName())
                        .append(" (").append(t.getStatusTask()).append(")")
                        .append(" - Duration: ").append(t.estimateDuration()).append("h\n");
            }
            sb.append("\n");
        }

        sb.append("\n=== All Tasks ===\n");
        for(Task t : tasksManagement.getTasks()) {
            sb.append("- ").append(t.getName())
                    .append(" (").append(t.getClass().getSimpleName()).append(")")
                    .append(" - Duration: ").append(t.estimateDuration()).append("h\n");
        }

        displayArea.setText(sb.toString());
    }
}