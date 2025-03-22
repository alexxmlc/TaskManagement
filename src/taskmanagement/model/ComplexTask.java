package taskmanagement.model;

import java.util.ArrayList;
import java.util.List;

public final class ComplexTask extends Task {
    private final List<SimpleTask> subTasks = new ArrayList<>();

    public ComplexTask(String name) {
        super(name);
    }

    public void addSubTask(SimpleTask task) {
        subTasks.add(task);
    }

    @Override
    public int estimateDuration() {
        return subTasks.stream().mapToInt(SimpleTask::estimateDuration).sum();
    }

    public List<SimpleTask> getSubTasks() {
        return new ArrayList<>(subTasks);
    }
}