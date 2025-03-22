package taskmanagement.model;

import java.io.Serializable;

public abstract sealed class Task implements Serializable permits SimpleTask, ComplexTask {
    private String name;
    private String statusTask = "Uncompleted";

    public Task(String name) {
        this.name = name;
    }

    public abstract int estimateDuration();

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatusTask() { return statusTask; }
    public void setStatusTask(String statusTask) { this.statusTask = statusTask; }
}