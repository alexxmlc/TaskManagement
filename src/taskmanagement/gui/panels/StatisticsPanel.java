package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.businesslogic.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StatisticsPanel extends JPanel {
    private final TasksManagement tm;
    private JTextArea statsArea;

    public StatisticsPanel(TasksManagement tm) {
        this.tm = tm;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statsArea);

        JButton refreshButton = new JButton("Refresh Statistics");
        refreshButton.addActionListener(e -> refreshStatistics());

        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        refreshStatistics();
    }

    private void refreshStatistics() {
        StringBuilder sb = new StringBuilder();

        // Employees over 40 hours
        sb.append("=== Employees with >40h Work Duration ===\n");
        List<String> overworked = Utility.filterEmployeesByDuration(tm);
        if (overworked.isEmpty()) {
            sb.append("No employees found\n");
        } else {
            overworked.forEach(name -> sb.append("- ").append(name).append("\n"));
        }

        // Task status counts
        sb.append("\n=== Task Status Counts ===\n");
        Map<String, Map<String, Integer>> statusMap = Utility.getTaskStatusCount(tm);
        statusMap.forEach((empName, counts) -> {
            sb.append(empName).append(":\n");
            sb.append("  Completed: ").append(counts.getOrDefault("Completed", 0)).append("\n");
            sb.append("  Uncompleted: ").append(counts.getOrDefault("Uncompleted", 0)).append("\n\n");
        });

        statsArea.setText(sb.toString());
    }
}