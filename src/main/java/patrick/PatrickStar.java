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
        assert filePath != null : "File path cannot be null";

        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

        assert ui != null : "UI should be initialized";
        assert storage != null : "Storage should be initialized";
        assert tasks != null : "TaskList should be initialized";
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
        return ui.formatWelcome();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user's input
     * @return Patrick's response
     */
    public String getResponse(String input) {
        return Parser.parseCommandForGui(input, tasks, ui, storage);
    }

    public static void main(String[] args) {
        new PatrickStar("./data/duke.txt").run();
    }
}
