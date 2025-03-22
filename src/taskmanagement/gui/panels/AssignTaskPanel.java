package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.model.Employee;
import taskmanagement.model.Task;

import javax.swing.*;
import java.awt.*;

public class AssignTaskPanel extends JPanel {
    private final TasksManagement tm;
    private JComboBox<Employee> employeeCombo;
    private JComboBox<Task> taskCombo;

    // Update constructor to auto-refresh
    public AssignTaskPanel(TasksManagement tm) {
        this.tm = tm;
        initializeUI();
        refreshCombos(); // Initial refresh
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        employeeCombo = new JComboBox<>(tm.getEmployees().toArray(new Employee[0]));
        taskCombo = new JComboBox<>(tm.getTasks().toArray(new Task[0]));

        inputPanel.add(new JLabel("Employee:"));
        inputPanel.add(employeeCombo);
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskCombo);

        JButton assignButton = new JButton("Assign Task");
        assignButton.addActionListener(e -> assignTask());

        JButton refreshButton = new JButton("Refresh Lists");
        refreshButton.addActionListener(e -> refreshCombos());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(assignButton);
        buttonPanel.add(refreshButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }



    private void refreshCombos() {
        employeeCombo.setModel(new DefaultComboBoxModel<>(tm.getEmployees().toArray(new Employee[0])));
        taskCombo.setModel(new DefaultComboBoxModel<>(tm.getTasks().toArray(new Task[0])));
    }

    private void assignTask() {
        Employee emp = (Employee) employeeCombo.getSelectedItem();
        Task task = (Task) taskCombo.getSelectedItem();

        if (emp == null || task == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select both employee and task!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            tm.assignTaskToEmployee(emp, task);

            // Verify assignment
            if (emp.getAssignedTasks().contains(task)) {
                JOptionPane.showMessageDialog(this,
                        "Task successfully assigned to " + emp.getName()
                );
            } else {
                JOptionPane.showMessageDialog(this,
                        "Assignment failed! Employee not registered properly.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Assignment Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}