import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + ", to: " + to.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " + to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}