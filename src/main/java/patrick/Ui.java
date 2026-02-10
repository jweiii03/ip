package patrick;

import java.util.Scanner;

import patrick.task.Task;

/**
 * Handles interactions with the user
 */
public class Ui {
    // Constants for index conversion and formatting
    private static final int DISPLAY_INDEX_OFFSET = 1; // Convert 0-indexed to 1-indexed for display
    private static final String TASK_NUMBER_SEPARATOR = ".";

    // Message templates
    private static final String TASK_COUNT_MESSAGE = "Now you have %d tasks in the list.";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Welcome message
     */
    public void showWelcome() {
        String logo = "######                                                 \n"
                + "#     #  ###   #####  #####   #   ####  #    #    #    \n"
                + "#     # #   #    #    #    #     #    # #   #     #    \n"
                + "######  #   #    #    #    #  #  #      ####      #    \n"
                + "#       #####    #    #####   #  #      #  #      #    \n"
                + "#       #   #    #    #   #   #  #    # #   #          \n"
                + "#       #   #    #    #    #  #   ####  #    #    #    \n";

        System.out.println("Hello from\n" + logo);
        System.out.println("Hi, I'm Patrick star.");
    }

    /**
     * Shows the goodbye message
     */
    public void showGoodbye() {
        System.out.println("What? Who you calling pinhead? Bye bye.");
    }

    /**
     * Shows an error message when loading fails
     */
    public void showLoadingError() {
        System.out.println("Uhhh... I couldn't load the tasks properly. Starting fresh!");
    }

    /**
     * Shows an error message
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Reads a command from the user
     * @return The command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a numbered list of tasks to console.
     * Helper method to reduce code duplication.
     *
     * @param tasks The list of tasks to print
     */
    private void printTaskList(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(getDisplayIndex(i) + TASK_NUMBER_SEPARATOR + tasks.get(i));
        }
    }

    /**
     * Formats a numbered list of tasks as a string.
     * Helper method to reduce code duplication.
     *
     * @param tasks The list of tasks to format
     * @return The formatted task list string
     */
    private String formatTaskListString(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(getDisplayIndex(i))
                    .append(TASK_NUMBER_SEPARATOR)
                    .append(tasks.get(i).toString())
                    .append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Converts 0-indexed position to 1-indexed display number.
     * Helper method to eliminate magic numbers.
     *
     * @param index The 0-indexed position
     * @return The 1-indexed display number
     */
    private int getDisplayIndex(int index) {
        return index + DISPLAY_INDEX_OFFSET;
    }

    /**
     * Formats the "Now you have X tasks" message.
     * Helper method to reduce string duplication.
     *
     * @param taskCount The number of tasks
     * @return The formatted message
     */
    private String formatTaskCountMessage(int taskCount) {
        return String.format(TASK_COUNT_MESSAGE, taskCount);
    }

    /**
     * Shows the list of tasks
     * @param tasks The list of tasks to display
     */
    public void showTaskList(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        System.out.println("Uhh... here are your tasks: ");
        printTaskList(tasks);
    }

    /**
     * Shows the matching tasks from a search.
     * @param tasks The list of matching tasks to display
     */
    public void showMatchingTasks(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        System.out.println("Uhhhhhhhhhhhh, here are the matching tasks in your list:");
        printTaskList(tasks);
    }

    /**
     * Shows a task that was marked as done
     * @param task The task that was marked
     */
    public void showTaskMarked(Task task) {
        assert task != null : "Task cannot be null";

        System.out.println("Alright, yeah. I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Shows a task that was unmarked
     * @param task The task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        assert task != null : "Task cannot be null";

        System.out.println("Alright I will unmark this task:");
        System.out.println(task);
    }

    /**
     * Shows a task that was deleted
     * @param task The task that was deleted
     * @param remainingTasks The number of tasks remaining
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        assert task != null : "Task cannot be null";
        assert remainingTasks >= 0 : "Remaining tasks count must be non-negative";

        System.out.println("Alright yeah. I will remove this task:");
        System.out.println("  " + task);
        System.out.println(formatTaskCountMessage(remainingTasks));
    }

    /**
     * Shows a task that was added
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        assert task != null : "Task cannot be null";
        assert totalTasks > 0 : "Total tasks must be positive after adding a task";
        showTaskAddedWithOptionalPrefix(task, totalTasks, "");
    }

    /**
     * Shows a task that was added (with "Uhhh..." prefix for events)
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     */
    public void showTaskAddedWithUhh(Task task, int totalTasks) {
        showTaskAddedWithOptionalPrefix(task, totalTasks, "Uhhh... ");
    }

    /**
     * Helper method to show task added with optional prefix.
     * Reduces duplication between showTaskAdded and showTaskAddedWithUhh.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     * @param prefix Optional prefix for the task count message
     */
    private void showTaskAddedWithOptionalPrefix(Task task, int totalTasks, String prefix) {
        assert task != null : "Task cannot be null";
        assert totalTasks > 0 : "Total tasks must be positive after adding a task";

        System.out.println("Alright. I've added this task:");
        System.out.println(task);
        System.out.println(prefix + formatTaskCountMessage(totalTasks));
    }

    /**
     * Closes the scanner
     */
    public void close() {
        scanner.close();
    }

    // ========== Methods for GUI (return strings instead of printing) ==========

    /**
     * Formats the welcome message for GUI.
     *
     * @return The welcome message
     */
    public String formatWelcome() {
        return "Hi, I'm Patrick Star.\nWhat can I do for you today?";
    }

    /**
     * Formats the goodbye message for GUI.
     *
     * @return The goodbye message
     */
    public String formatGoodbye() {
        return "What? Who you calling pinhead? Bye bye.";
    }

    /**
     * Formats the task list as a string for GUI.
     *
     * @param tasks The list of tasks to format
     * @return The formatted task list
     */
    public String formatTaskList(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        if (tasks.isEmpty()) {
            return "Uhh... you don't have any tasks yet!";
        }

        String result = "Uhh... here are your tasks:\n" + formatTaskListString(tasks);
        assert result != null : "Formatted task list should not be null";
        return result;
    }

    /**
     * Formats matching tasks from a search as a string for GUI.
     *
     * @param tasks The list of matching tasks to format
     * @return The formatted matching tasks
     */
    public String formatMatchingTasks(java.util.ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        if (tasks.isEmpty()) {
            return "Uhh... I couldn't find any tasks with that keyword!";
        }

        String result = "Uhhhhhhhhhhhh, here are the matching tasks in your list:\n"
                + formatTaskListString(tasks);
        assert result != null : "Formatted matching tasks should not be null";
        return result;
    }

    /**
     * Formats a task marked message for GUI.
     *
     * @param task The task that was marked
     * @return The formatted message
     */
    public String formatTaskMarked(Task task) {
        assert task != null : "Task cannot be null";

        String result = "Alright, yeah. I've marked this task as done:\n" + task.toString();
        assert result != null : "Formatted message should not be null";
        return result;
    }

    /**
     * Formats a task unmarked message for GUI.
     *
     * @param task The task that was unmarked
     * @return The formatted message
     */
    public String formatTaskUnmarked(Task task) {
        assert task != null : "Task cannot be null";

        String result = "Alright I will unmark this task:\n" + task.toString();
        assert result != null : "Formatted message should not be null";
        return result;
    }

    /**
     * Formats a task deleted message for GUI.
     *
     * @param task The task that was deleted
     * @param remainingTasks The number of tasks remaining
     * @return The formatted message
     */
    public String formatTaskDeleted(Task task, int remainingTasks) {
        assert task != null : "Task cannot be null";
        assert remainingTasks >= 0 : "Remaining tasks count must be non-negative";

        String result = "Alright yeah. I will remove this task:\n  " + task.toString()
                + "\n" + formatTaskCountMessage(remainingTasks);
        assert result != null : "Formatted message should not be null";
        return result;
    }

    /**
     * Formats a task added message for GUI.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     * @return The formatted message
     */
    public String formatTaskAdded(Task task, int totalTasks) {
        assert task != null : "Task cannot be null";
        assert totalTasks > 0 : "Total tasks must be positive after adding a task";

        String result = "Alright. I've added this task:\n" + task.toString()
                + "\n" + formatTaskCountMessage(totalTasks);
        assert result != null : "Formatted message should not be null";
        return result;
    }

    /**
     * Shows the help message with all available commands
     */
    public void showHelp() {
        System.out.println("Uhhh... Here are the commands that I know of:");
        System.out.println("• list - Shows all your tasks");
        System.out.println("• todo <description> - Adds a new todo task");
        System.out.println("• deadline <description> /by <yyyy-MM-dd> - Adds a deadline");
        System.out.println("• event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm> - Adds an event");
        System.out.println("• mark <task number> - Marks a task as done");
        System.out.println("• unmark <task number> - Marks a task as not done");
        System.out.println("• delete <task number> - Deletes a task");
        System.out.println("• find <keyword> - Searches for tasks containing the keyword");
        System.out.println("• help - Shows this help message");
        System.out.println("• bye - Exits the application");
    }

    /**
     * Formats the help message for GUI
     *
     * @return The formatted help message
     */
    public String formatHelp() {
        return "Uhhh... Here are the commands that I know of:\n\n"
                + "• list - Shows all your tasks\n"
                + "• todo <description> - Adds a new todo task\n"
                + "• deadline <description> /by <yyyy-MM-dd> - Adds a deadline\n"
                + "• event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm> - Adds an event\n"
                + "• mark <task number> - Marks a task as done\n"
                + "• unmark <task number> - Marks a task as not done\n"
                + "• delete <task number> - Deletes a task\n"
                + "• find <keyword> - Searches for tasks containing the keyword\n"
                + "• help - Shows this help message\n"
                + "• bye - Exits the application";
    }
}
