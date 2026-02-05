package patrick.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor: Creates a new Event task with the given description and time period
     *
     * @param description The description of the event
     * @param from The start time of the event
     * @param to The end time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event
     *
     * @return The start time
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event
     *
     * @return The end time
     */
    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from.toString() + " | " + to.toString();
    }
}
