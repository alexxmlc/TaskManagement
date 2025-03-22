package taskmanagement.model;

public final class SimpleTask extends Task {
    private final int startHour;
    private final int endHour;

    public SimpleTask(String name, int startHour, int endHour) {
        super(name);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }

    // Getters
    public int getStartHour() { return startHour; }
    public int getEndHour() { return endHour; }
}