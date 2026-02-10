package patrick;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import patrick.task.Deadline;
import patrick.task.Event;
import patrick.task.Task;
import patrick.task.ToDo;

/**
 * Parses user input and executes commands.
 */

public class Parser {
    // Constants for date/time formats
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HHmm";

    // Constants for command parsing
    private static final int COMMAND_SPLIT_LIMIT = 2;
    private static final int TASK_INDEX_OFFSET = 1; // Convert 1-indexed user input to 0-indexed array

    // Constants for command delimiters
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_DELIMITER_REGEX = " /from | /to ";

    // Constants for minimum parts needed after splitting
    private static final int MIN_PARTS_WITH_ARGUMENT = 2;
    private static final int MIN_EVENT_PARTS = 3;

    /**
     * Parses and executes a user command for GUI (returns response string).
     *
     * @param input The user input
     * @param tasks The task list
     * @param ui The UI handler
     * @param storage The storage handler
     * @return The response string to display in GUI
     */
    public static String parseCommandForGui(String input, TaskList tasks, Ui ui, Storage storage) {
        try {
            String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
            String command = parts[0].toLowerCase();

            switch (command) {
            case "bye":
                return ui.formatGoodbye();

            case "list":
                return ui.formatTaskList(tasks.getTasks());

            case "mark":
                return handleMarkForGui(parts, tasks, ui, storage);

            case "unmark":
                return handleUnmarkForGui(parts, tasks, ui, storage);

            case "delete":
                return handleDeleteForGui(parts, tasks, ui, storage);

            case "todo":
                return handleTodoForGui(parts, tasks, ui, storage);

            case "deadline":
                return handleDeadlineForGui(parts, tasks, ui, storage);

            case "event":
                return handleEventForGui(parts, tasks, ui, storage);

            case "find":
                return handleFindForGui(parts, tasks, ui);

            default:
                throw new DukeException("Uhhh... I don't understand what that means. Is mayonnaise a command?");
            }
        } catch (DukeException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Uhhh... that doesn't look like a number to me...";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "I think something's missing in your command";
        }
    }

    /**
     * Parses and executes a user command
     * @param input The user input
     * @param tasks The task list
     * @param ui The UI handler
     * @param storage The storage handler
     * @return true if the command is "bye", false otherwise
     * @throws DukeException if there's an error executing the command
     */
    public static boolean parseCommand(String input, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        String command = parts[0].toLowerCase();

        try {
            switch (command) {
            case "bye":
                return true;

            case "list":
                ui.showTaskList(tasks.getTasks());
                break;

            case "mark":
                handleMark(parts, tasks, ui, storage);
                break;

            case "unmark":
                handleUnmark(parts, tasks, ui, storage);
                break;

            case "delete":
                handleDelete(parts, tasks, ui, storage);
                break;

            case "todo":
                handleTodo(parts, tasks, ui, storage);
                break;

            case "deadline":
                handleDeadline(parts, tasks, ui, storage);
                break;

            case "event":
                handleEvent(parts, tasks, ui, storage);
                break;

            case "find":
                handleFind(parts, tasks, ui);
                break;

            default:
                throw new DukeException("Uhhh... I don't understand what that means. Is mayonnaise a command?");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Uhhh... that doesn't look like a number to me...");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("I think something's missing in your command");
        }

        return false;
    }

    /**
     * Validates that command has required number of parts.
     *
     * @param parts The split command parts
     * @param minParts Minimum number of parts required
     * @param errorMessage Error message if validation fails
     * @throws DukeException if validation fails
     */
    private static void validateCommandParts(String[] parts, int minParts, String errorMessage)
            throws DukeException {
        if (parts.length < minParts) {
            throw new DukeException(errorMessage);
        }
    }

    /**
     * Parses task number from command parts (converts from 1-indexed to 0-indexed).
     *
     * @param parts The split command parts
     * @return The 0-indexed task number
     * @throws NumberFormatException if parts[1] is not a valid integer
     */
    private static int parseTaskNumber(String[] parts) {
        return Integer.parseInt(parts[1]) - TASK_INDEX_OFFSET;
    }

    /**
     * Handles the mark command to mark a task as done.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If task number is invalid or missing.
     */
    private static void handleMark(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do I mark?");
        int taskNum = parseTaskNumber(parts);
        assert taskNum >= -1 : "Task number after parsing should be >= -1";

        tasks.markTask(taskNum);
        ui.showTaskMarked(tasks.getTask(taskNum));
        storage.save(tasks.getTasks());
    }

    /**
     * Handles the unmark command to mark a task as not done.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If task number is invalid or missing.
     */
    private static void handleUnmark(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do I unmark?");
        int taskNum = parseTaskNumber(parts);
        tasks.unmarkTask(taskNum);
        ui.showTaskUnmarked(tasks.getTask(taskNum));
        storage.save(tasks.getTasks());
    }

    /**
     * Handles the delete command to remove a task.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If task number is invalid or missing.
     */
    private static void handleDelete(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do you want me to delete?");
        int taskNum = parseTaskNumber(parts);
        Task deletedTask = tasks.deleteTask(taskNum);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getTasks());
    }

    /**
     * Handles the todo command to create a new todo task.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If description is missing.
     */
    private static void handleTodo(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... What is the name of the ToDo task again?");
        }
        String description = parts[1];
        Task task = new ToDo(description);
        assert task != null : "Created task should not be null";

        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.size());
        storage.save(tasks.getTasks());
    }

    /**
     * Handles the deadline command to create a new deadline task.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If description or date is missing or invalid.
     */
    private static void handleDeadline(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the deadline...");
        }
        if (!parts[1].contains("/by")) {
            throw new DukeException("Huh? When is the deadline? Use '/by' to tell me");
        }
        String[] deadlineParts = parts[1].split(DEADLINE_DELIMITER);
        if (deadlineParts.length < MIN_PARTS_WITH_ARGUMENT || deadlineParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the deadline description can't be empty Yeah");
        }
        String description = deadlineParts[0];
        String byString = deadlineParts[1].trim();

        try {
            LocalDate by = LocalDate.parse(byString, DateTimeFormatter.ofPattern(DATE_FORMAT));
            Task task = new Deadline(description, by);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.size());
            storage.save(tasks.getTasks());
        } catch (DateTimeParseException e) {
            throw new DukeException("Uhhh... I need the date in yyyy-MM-dd format (like 2019-10-15)");
        }
    }

    /**
     * Handles the event command to create a new event task.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If description or times are missing or invalid.
     */
    private static void handleEvent(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the event Yeah");
        }
        if (!parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new DukeException("Huh? When is the event? Use '/from' and '/to'");
        }
        String[] eventParts = parts[1].split(EVENT_DELIMITER_REGEX);
        if (eventParts.length < MIN_EVENT_PARTS || eventParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the event description can't be empty Yeah");
        }
        String description = eventParts[0];
        String fromString = eventParts[1].trim();
        String toString = eventParts[2].trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime from = LocalDateTime.parse(fromString, formatter);
            LocalDateTime to = LocalDateTime.parse(toString, formatter);
            Task task = new Event(description, from, to);
            tasks.addTask(task);
            ui.showTaskAddedWithUhh(task, tasks.size());
            storage.save(tasks.getTasks());
        } catch (DateTimeParseException e) {
            throw new DukeException("Uhhh... I need dates in yyyy-MM-dd HHmm format (like 2019-10-15 1800)");
        }
    }

    /**
     * Handles the find command to search for tasks containing a given keyword
     *
     * @param parts The split command parts, where the second element contains the search keyword
     * @param tasks The task list to search within
     * @param ui The UI handler used to display matching tasks
     * @throws DukeException If the search keyword is missing or empty
     */
    private static void handleFind(String[] parts, TaskList tasks, Ui ui) throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... what should I search for?");
        }
        String keyword = parts[1].trim();
        java.util.ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }

    // ========== GUI-specific methods that return strings ==========

    /**
     * Handles the mark command for GUI and returns response string.
     */
    private static String handleMarkForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do I mark?");
        int taskNum = parseTaskNumber(parts);
        tasks.markTask(taskNum);
        storage.save(tasks.getTasks());
        return ui.formatTaskMarked(tasks.getTask(taskNum));
    }

    /**
     * Handles the unmark command for GUI and returns response string.
     */
    private static String handleUnmarkForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do I unmark?");
        int taskNum = parseTaskNumber(parts);
        tasks.unmarkTask(taskNum);
        storage.save(tasks.getTasks());
        return ui.formatTaskUnmarked(tasks.getTask(taskNum));
    }

    /**
     * Handles the delete command for GUI and returns response string.
     */
    private static String handleDeleteForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        validateCommandParts(parts, MIN_PARTS_WITH_ARGUMENT, "Uhhh... which task do you want me to delete?");
        int taskNum = parseTaskNumber(parts);
        Task deletedTask = tasks.deleteTask(taskNum);
        storage.save(tasks.getTasks());
        return ui.formatTaskDeleted(deletedTask, tasks.size());
    }

    /**
     * Handles the todo command for GUI and returns response string.
     */
    private static String handleTodoForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... What is the name of the ToDo task again?");
        }
        String description = parts[1];
        Task task = new ToDo(description);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return ui.formatTaskAdded(task, tasks.size());
    }

    /**
     * Handles the deadline command for GUI and returns response string.
     */
    private static String handleDeadlineForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the deadline...");
        }
        if (!parts[1].contains("/by")) {
            throw new DukeException("Huh? When is the deadline? Use '/by' to tell me");
        }
        String[] deadlineParts = parts[1].split(DEADLINE_DELIMITER);
        if (deadlineParts.length < MIN_PARTS_WITH_ARGUMENT || deadlineParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the deadline description can't be empty Yeah");
        }
        String description = deadlineParts[0];
        String byString = deadlineParts[1].trim();

        try {
            LocalDate by = LocalDate.parse(byString, DateTimeFormatter.ofPattern(DATE_FORMAT));
            Task task = new Deadline(description, by);
            tasks.addTask(task);
            storage.save(tasks.getTasks());
            return ui.formatTaskAdded(task, tasks.size());
        } catch (DateTimeParseException e) {
            throw new DukeException("Uhhh... I need the date in yyyy-MM-dd format (like 2019-10-15)");
        }
    }

    /**
     * Handles the event command for GUI and returns response string.
     */
    private static String handleEventForGui(String[] parts, TaskList tasks, Ui ui, Storage storage)
            throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the event Yeah");
        }
        if (!parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new DukeException("Huh? When is the event? Use '/from' and '/to'");
        }
        String[] eventParts = parts[1].split(EVENT_DELIMITER_REGEX);
        if (eventParts.length < MIN_EVENT_PARTS || eventParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the event description can't be empty Yeah");
        }
        String description = eventParts[0];
        String fromString = eventParts[1].trim();
        String toString = eventParts[2].trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime from = LocalDateTime.parse(fromString, formatter);
            LocalDateTime to = LocalDateTime.parse(toString, formatter);
            Task task = new Event(description, from, to);
            tasks.addTask(task);
            storage.save(tasks.getTasks());
            return ui.formatTaskAdded(task, tasks.size());
        } catch (DateTimeParseException e) {
            throw new DukeException("Uhhh... I need dates in yyyy-MM-dd HHmm format (like 2019-10-15 1800)");
        }
    }

    /**
     * Handles the find command for GUI and returns response string.
     */
    private static String handleFindForGui(String[] parts, TaskList tasks, Ui ui) throws DukeException {
        if (parts.length < MIN_PARTS_WITH_ARGUMENT || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... what should I search for?");
        }
        String keyword = parts[1].trim();
        java.util.ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        return ui.formatMatchingTasks(matchingTasks);
    }
}
