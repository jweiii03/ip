package patrick.task;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    // Mark task as completed
    public void markAsDone() {
        isDone = true;
    }

    // Unmark task
    public void markAsNotDone() {
        isDone = false;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    // File format for task
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}
