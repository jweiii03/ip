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
        String[] parts = input.split(" ", 2);
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
     * Handles the mark command to mark a task as done.
     *
     * @param parts The split command parts.
     * @param tasks The task list.
     * @param ui The UI handler.
     * @param storage The storage handler.
     * @throws DukeException If task number is invalid or missing.
     */
    private static void handleMark(String[] parts, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (parts.length < 2) {
            throw new DukeException("Uhhh... which task do I mark?");
        }
        int taskNum = Integer.parseInt(parts[1]) - 1;
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
        if (parts.length < 2) {
            throw new DukeException("Uhhh... which task do I unmark?");
        }
        int taskNum = Integer.parseInt(parts[1]) - 1;
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
        if (parts.length < 2) {
            throw new DukeException("Uhhh... which task do you want me to delete?");
        }
        int taskNum = Integer.parseInt(parts[1]) - 1;
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
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... What is the name of the ToDo task again?");
        }
        String description = parts[1];
        Task task = new ToDo(description);
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
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the deadline...");
        }
        if (!parts[1].contains("/by")) {
            throw new DukeException("Huh? When is the deadline? Use '/by' to tell me");
        }
        String[] deadlineParts = parts[1].split(" /by ");
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the deadline description can't be empty Yeah");
        }
        String description = deadlineParts[0];
        String byString = deadlineParts[1].trim();

        try {
            LocalDate by = LocalDate.parse(byString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... I need a description for the event Yeah");
        }
        if (!parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new DukeException("Huh? When is the event? Use '/from' and '/to'");
        }
        String[] eventParts = parts[1].split(" /from | /to ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty()) {
            throw new DukeException("Uhhh... the event description can't be empty Yeah");
        }
        String description = eventParts[0];
        String fromString = eventParts[1].trim();
        String toString = eventParts[2].trim();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
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
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("Uhhh... what should I search for?");
        }
        String keyword = parts[1].trim();
        java.util.ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }
}