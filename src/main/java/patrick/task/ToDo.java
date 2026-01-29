package patrick.task;

/**
 * Represents a todo task without any date/time attached
 */
public class ToDo extends Task {
    /**
     * Constructor: Creates a new ToDo task with the given description
     *
     * @param description The description of the todo task
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }
}
