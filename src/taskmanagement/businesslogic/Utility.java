package taskmanagement.businesslogic;

import taskmanagement.model.Employee;
import taskmanagement.model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Utility {
    public static List<String> filterEmployeesByDuration(TasksManagement tm) {
        return tm.getEmployees().stream()
                .filter(e -> tm.calculateEmployeeWorkDuration(e) > 40)
                .sorted(Comparator.comparingInt(e -> tm.calculateEmployeeWorkDuration(e)))
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    public static Map<String, Map<String, Integer>> getTaskStatusCount(TasksManagement tm) {
        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
        for (Employee e : tm.getEmployees()) {
            Map<String, Integer> counts = new HashMap<>();
            counts.put("Completed", 0);
            counts.put("Uncompleted", 0);

            for (Task t : e.getAssignedTasks()) {
                String status = t.getStatusTask();
                counts.put(status, counts.getOrDefault(status, 0) + 1);
            }
            result.put(e.getName(), counts);
        }
        return result;
    }
}