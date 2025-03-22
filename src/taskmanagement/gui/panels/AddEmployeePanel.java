package taskmanagement.gui.panels;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.model.Employee;

import javax.swing.*;
import java.awt.*;

public class AddEmployeePanel extends JPanel {
    private final TasksManagement tm;
    private final JTextField nameField = new JTextField(20);

    public AddEmployeePanel(TasksManagement tm) {
        this.tm = tm;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Employee Name:"));
        inputPanel.add(nameField);

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(e -> {
            tm.addEmployee(new Employee(nameField.getText()));
            nameField.setText("");
            JOptionPane.showMessageDialog(this, "Employee added!");
        });

        add(inputPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }
}