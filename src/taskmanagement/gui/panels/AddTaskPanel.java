package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.model.ComplexTask;
import taskmanagement.model.SimpleTask;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddTaskPanel extends JPanel {
    private final TasksManagement tm;
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Simple Task", "Complex Task"});
    private JSpinner startSpinner, endSpinner;
    private JList<SimpleTask> taskList;

    public AddTaskPanel(TasksManagement tm) {
        this.tm = tm;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Common fields
        mainPanel.add(new JLabel("Task Type:"));
        mainPanel.add(typeCombo);
        mainPanel.add(new JLabel("Task Name:"));
        mainPanel.add(nameField);

        // Dynamic fields
        JPanel dynamicPanel = new JPanel(new BorderLayout());
        typeCombo.addActionListener(e -> updateDynamicFields(dynamicPanel));
        updateDynamicFields(dynamicPanel);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        add(mainPanel, BorderLayout.NORTH);
        add(dynamicPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    private void updateDynamicFields(JPanel panel) {
        panel.removeAll();
        if (typeCombo.getSelectedItem().equals("Simple Task")) {
            startSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
            endSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

            JPanel timePanel = new JPanel(new GridLayout(2, 2));
            timePanel.add(new JLabel("Start Hour:"));
            timePanel.add(startSpinner);
            timePanel.add(new JLabel("End Hour:"));
            timePanel.add(endSpinner);

            panel.add(timePanel, BorderLayout.NORTH);
        } else {
            List<SimpleTask> tasks = tm.getAllSimpleTasks();
            taskList = new JList<>(tasks.toArray(new SimpleTask[0]));
            taskList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            panel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void addTask() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Task name cannot be empty!");
            return;
        }

        try {
            if (typeCombo.getSelectedItem().equals("Simple Task")) {
                int start = (Integer) startSpinner.getValue();
                int end = (Integer) endSpinner.getValue();
                if (start >= end) throw new IllegalArgumentException();
                tm.addTask(new SimpleTask(name, start, end));
            } else {
                List<SimpleTask> selected = taskList.getSelectedValuesList();
                if (selected.isEmpty()) {
                    showError("Select at least one subtask!");
                    return;
                }
                ComplexTask ct = new ComplexTask(name);
                selected.forEach(ct::addSubTask);
                tm.addTask(ct);
            }
            nameField.setText("");
            JOptionPane.showMessageDialog(this, "Task added!");
        } catch (IllegalArgumentException e) {
            showError("Invalid time values! End must be after start.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}