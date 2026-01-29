package patrick.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline
 */
public class Deadline extends Task {
    // Child class of Task parent class
    private LocalDate dueDate;

    /**
     * Constructor: Creates a new Deadline task with the given description and due date
     *
     * @param description The description of the deadline task
     * @param dueDate The date by which the task should be completed
     */
    public Deadline(String description, LocalDate dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    /**
     * Returns the due date of the deadline
     *
     * @return The due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}