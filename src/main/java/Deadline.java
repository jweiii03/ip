import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    // Child class of Task parent class
    private LocalDate dueDate;

    public Deadline(String description, LocalDate dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

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