package patrick.task;

/**
 * Represents a task with a description and completion status.
 */

public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a new Task with the given description
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task
     * "X" indicates the task is completed, while a blank space indicates it is not completed
     *
     * @return A string representing the task's completion status
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as done
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string in the format "[status] description"
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the task in file format for saving to disk.
     *
     * @return A string representation of the task for file storage.
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}
