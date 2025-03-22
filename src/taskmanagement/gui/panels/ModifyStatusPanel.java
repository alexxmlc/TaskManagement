package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.model.Employee;
import taskmanagement.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ModifyStatusPanel extends JPanel {
    private final TasksManagement tm;
    private JComboBox<Employee> employeeCombo;
    private JComboBox<Task> taskCombo;
    private JRadioButton completedRadio;
    private JRadioButton uncompletedRadio;

    public ModifyStatusPanel(TasksManagement tm) {
        this.tm = tm;
        initializeUI();
        refreshEmployeeList(); // Initial population
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Top panel with comboboxes
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        // Employee selection
        employeeCombo = new JComboBox<>();
        employeeCombo.addItemListener(this::employeeSelected);
        topPanel.add(new JLabel("Select Employee:"));
        topPanel.add(employeeCombo);

        // Task selection
        taskCombo = new JComboBox<>();
        topPanel.add(new JLabel("Select Task:"));
        topPanel.add(taskCombo);

        // Status radio buttons
        JPanel statusPanel = new JPanel();
        completedRadio = new JRadioButton("Completed");
        uncompletedRadio = new JRadioButton("Uncompleted");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(completedRadio);
        statusGroup.add(uncompletedRadio);
        statusPanel.add(completedRadio);
        statusPanel.add(uncompletedRadio);

        topPanel.add(new JLabel("New Status:"));
        topPanel.add(statusPanel);

        // Control buttons
        JButton refreshButton = new JButton("Refresh Lists");
        refreshButton.addActionListener(e -> refreshEmployeeList());

        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(e -> updateStatus());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshEmployeeList() {
        employeeCombo.removeAllItems();
        tm.getEmployees().forEach(employeeCombo::addItem);
    }

    private void employeeSelected(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Employee selected = (Employee) e.getItem();
            refreshTaskList(selected);
        }
    }

    private void refreshTaskList(Employee employee) {
        taskCombo.removeAllItems();
        employee.getAssignedTasks().forEach(taskCombo::addItem);
    }

    private void updateStatus() {
        Employee employee = (Employee) employeeCombo.getSelectedItem();
        Task task = (Task) taskCombo.getSelectedItem();

        if (employee == null || task == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select both employee and task!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String newStatus = completedRadio.isSelected() ? "Completed" : "Uncompleted";
        tm.modifyTaskStatus(employee, task, newStatus);
        JOptionPane.showMessageDialog(this, "Status updated successfully!");
    }
}