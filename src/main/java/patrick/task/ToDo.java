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
        String formatted = "T | " + super.toFileFormat();
        // #region agent log
        try (java.io.FileWriter fw = new java.io.FileWriter("/Users/hjw/Documents/CS2103T/Ip"
                + "/.cursor/debug.log", true)) {
            String payload = String.format(
                    "{\"sessionId\":\"debug-session\",\"runId\":\"pre-fix\","
                            + "\"hypothesisId\":\"H3\",\"location\":\"ToDo.toFileFormat\","
                            + "\"message\":\"toFileFormat\",\"data\":{\"value\":\"%s\"},"
                            + "\"timestamp\":%d}\n",
                    formatted.replace("\"", "\\\""),
                    System.currentTimeMillis());
            fw.write(payload);
        } catch (java.io.IOException ignored) {
            // swallow
        }
        // #endregion
        return formatted;
    }
}
