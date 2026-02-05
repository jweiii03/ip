package patrick;

/**
 * PatrickStar chatbot that manages tasks
 */
public class PatrickStar {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor: Creates a new PatrickStar instance and initializes the UI, storage, and task list
     *
     * @param filePath The file path to store and load tasks from
     */
    public PatrickStar(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the chatbot, processing user commands until user exits
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                isExit = Parser.parseCommand(fullCommand, tasks, ui, storage);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showGoodbye();
        ui.close();
    }

    /**
     * Generates a welcome message for the GUI.
     *
     * @return The welcome message
     */
    public String getWelcomeMessage() {
        return "Hi, I'm Patrick Star.\nWhat can I do for you today?";
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user's input
     * @return Patrick's response
     */
    public String getResponse(String input) {
        try {
            // Check if it's a bye command
            boolean isExit = Parser.parseCommand(input, tasks, ui, storage);

            if (isExit) {
                return "What? Who you calling pinhead? Bye bye.";
            }

            // For other commands, we need to capture the output
            // Since the Parser uses Ui to display messages, we'll need to refactor it
            // For now, let's create a simple response
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
            case "list":
                return getTaskListString();
            case "mark":
            case "unmark":
            case "delete":
            case "todo":
            case "deadline":
            case "event":
            case "find":
                // Execute the command (it will update the task list)
                Parser.parseCommand(input, tasks, ui, storage);
                return getLastActionResponse(command);
            default:
                return "Uhhh... I don't understand what that means. Is mayonnaise a command?";
            }
        } catch (DukeException e) {
            return e.getMessage();
        }
    }

    /**
     * Gets the task list as a formatted string.
     *
     * @return The formatted task list
     */
    private String getTaskListString() {
        if (tasks.size() == 0) {
            return "Uhh... you don't have any tasks yet!";
        }
        StringBuilder sb = new StringBuilder("Uhh... here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                sb.append((i + 1)).append(".").append(tasks.getTask(i).toString()).append("\n");
            } catch (DukeException e) {
                // Should not happen
            }
        }
        return sb.toString().trim();
    }

    /**
     * Gets a response message for the last action performed.
     *
     * @param command The command that was executed
     * @return A confirmation message
     */
    private String getLastActionResponse(String command) {
        switch (command) {
        case "mark":
            return "Alright, yeah. I've marked this task as done!";
        case "unmark":
            return "Alright I will unmark this task.";
        case "delete":
            return "Alright yeah. I will remove this task!\nNow you have " + tasks.size() + " tasks in the list.";
        case "todo":
        case "deadline":
        case "event":
            return "Alright. I've added this task:\n" + getLastTask()
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
        case "find":
            return "Let me search for that...";
        default:
            return "Done!";
        }
    }

    /**
     * Gets the last task in the list as a string.
     *
     * @return The last task
     */
    private String getLastTask() {
        try {
            return tasks.getTask(tasks.size() - 1).toString();
        } catch (DukeException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        new PatrickStar("./data/duke.txt").run();
    }
}
