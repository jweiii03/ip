package patrick;

import java.util.Scanner;

import patrick.task.Task;

/**
 * Handles interactions with the user
 */
public class Ui {
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
     * Shows the list of tasks
     * @param tasks The list of tasks to display
     */
    public void showTaskList(java.util.ArrayList<Task> tasks) {
        System.out.println("Uhh... here are your tasks: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows the matching tasks from a search.
     * @param tasks The list of matching tasks to display
     */
    public void showMatchingTasks(java.util.ArrayList<Task> tasks) {
        System.out.println("Uhhhhhhhhhhhh, here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows a task that was marked as done
     * @param task The task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println("Alright, yeah. I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Shows a task that was unmarked
     * @param task The task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Alright I will unmark this task:");
        System.out.println(task);
    }

    /**
     * Shows a task that was deleted
     * @param task The task that was deleted
     * @param remainingTasks The number of tasks remaining
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        System.out.println("Alright yeah. I will remove this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + remainingTasks + " tasks in the list.");
    }

    /**
     * Shows a task that was added
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Alright. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Shows a task that was added (with "Uhhh..." prefix for events)
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     */
    public void showTaskAddedWithUhh(Task task, int totalTasks) {
        System.out.println("Alright. I've added this task:");
        System.out.println(task);
        System.out.println("Uhhh... Now you have " + totalTasks + " tasks in the list.");
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
        if (tasks.isEmpty()) {
            return "Uhh... you don't have any tasks yet!";
        }
        StringBuilder sb = new StringBuilder("Uhh... here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Formats matching tasks from a search as a string for GUI.
     *
     * @param tasks The list of matching tasks to format
     * @return The formatted matching tasks
     */
    public String formatMatchingTasks(java.util.ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "Uhh... I couldn't find any tasks with that keyword!";
        }
        StringBuilder sb = new StringBuilder("Uhhhhhhhhhhhh, here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Formats a task marked message for GUI.
     *
     * @param task The task that was marked
     * @return The formatted message
     */
    public String formatTaskMarked(Task task) {
        return "Alright, yeah. I've marked this task as done:\n" + task.toString();
    }

    /**
     * Formats a task unmarked message for GUI.
     *
     * @param task The task that was unmarked
     * @return The formatted message
     */
    public String formatTaskUnmarked(Task task) {
        return "Alright I will unmark this task:\n" + task.toString();
    }

    /**
     * Formats a task deleted message for GUI.
     *
     * @param task The task that was deleted
     * @param remainingTasks The number of tasks remaining
     * @return The formatted message
     */
    public String formatTaskDeleted(Task task, int remainingTasks) {
        return "Alright yeah. I will remove this task:\n  " + task.toString()
                + "\nNow you have " + remainingTasks + " tasks in the list.";
    }

    /**
     * Formats a task added message for GUI.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks
     * @return The formatted message
     */
    public String formatTaskAdded(Task task, int totalTasks) {
        return "Alright. I've added this task:\n" + task.toString()
                + "\nNow you have " + totalTasks + " tasks in the list.";
    }
}
