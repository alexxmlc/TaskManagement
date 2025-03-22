package taskmanagement.businesslogic;

import taskmanagement.model.Employee;
import taskmanagement.model.SimpleTask;
import taskmanagement.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TasksManagement implements Serializable {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();

    public void assignTaskToEmployee(Employee employee, Task task) {
        if (employees.contains(employee)) { // Fix missing parenthesis
            employee.getAssignedTasks().add(task);
        } else {
            System.err.println("Employee not found in system!");
        }
    }

    public int calculateEmployeeWorkDuration(Employee employee) {
        return employee.getAssignedTasks().stream()
                .filter(t -> "Completed".equals(t.getStatusTask()))
                .mapToInt(Task::estimateDuration)
                .sum();
    }

    public void modifyTaskStatus(Employee employee, Task task, String newStatus) {
        if (employee.getAssignedTasks().contains(task)) {
            task.setStatusTask(newStatus);
        }
    }

    // Helper methods
    public void addEmployee(Employee employee) { employees.add(employee); }
    public void addTask(Task task) { tasks.add(task); }
    public List<Employee> getEmployees() { return new ArrayList<>(employees); }
    public List<Task> getTasks() { return new ArrayList<>(tasks); }

    public List<SimpleTask> getAllSimpleTasks() {
        return tasks.stream()
                .filter(t -> t instanceof SimpleTask)
                .map(t -> (SimpleTask) t)
                .collect(Collectors.toList());
    }
}