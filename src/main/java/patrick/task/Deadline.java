package patrick.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a specific due date
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Constructor: Creates a new Deadline task with the given description and due date
     *
     * @param description The description of the deadline task
     * @param by The due date of the task
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        assert by != null : "Deadline due date cannot be null";
        this.by = by;
    }

    /**
     * Returns the due date of the deadline
     *
     * @return The due date
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + by.toString();
    }
}
