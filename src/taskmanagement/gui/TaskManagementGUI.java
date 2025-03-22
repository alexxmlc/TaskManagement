package taskmanagement.gui;

import taskmanagement.businesslogic.TasksManagement;
import taskmanagement.gui.panels.*;
import taskmanagement.persistence.DataSerializer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TaskManagementGUI extends JFrame {
    private final TasksManagement tasksManagement;

    public TaskManagementGUI() {
        tasksManagement = DataSerializer.deserializeData("tasks.dat");
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Employee", new AddEmployeePanel(tasksManagement));
        tabbedPane.addTab("Add Task", new AddTaskPanel(tasksManagement));
        tabbedPane.addTab("Assign Task", new AssignTaskPanel(tasksManagement));
        tabbedPane.addTab("View Data", new ViewDataPanel(tasksManagement));
        tabbedPane.addTab("Statistics", new StatisticsPanel(tasksManagement));
        tabbedPane.addTab("Modify Status", new ModifyStatusPanel(tasksManagement));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataSerializer.serializeData(tasksManagement, "tasks.dat");
            }
        });

        add(tabbedPane);
        setLocationRelativeTo(null);
    }
}