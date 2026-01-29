import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PatrickStar {
    private static final String FILE_PATH = "./data/duke.txt";

    // Save tasks to file
    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            // Create directory if it doesn't exist
            Path path = Paths.get(FILE_PATH);
            Files.createDirectories(path.getParent());

            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Uhhh... I couldn't save the tasks. Sorry!");
        }
    }

    // Load tasks from file
    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
                    Task task = parseTaskFromFile(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Skip corrupted lines
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Uhhh... I couldn't find the file to load tasks.");
        }

        return tasks;
    }

    // Parse a task from file format
    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null; // Invalid format
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        try {
            if (type.equals("T")) {
                task = new ToDo(description);
            } else if (type.equals("D") && parts.length >= 4) {
                LocalDate by = LocalDate.parse(parts[3], DateTimeFormatter.ISO_LOCAL_DATE);
                task = new Deadline(description, by);
            } else if (type.equals("E") && parts.length >= 5) {
                LocalDateTime from = LocalDateTime.parse(parts[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime to = LocalDateTime.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                task = new Event(description, from, to);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date from file: " + line);
            return null;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    public static void main(String[] args) {
        String logo = "######                                                 \n"
                + "#     #  ###   #####  #####   #   ####  #    #    #    \n"
                + "#     # #   #    #    #    #     #    # #   #     #    \n"
                + "######  #   #    #    #    #  #  #      ####      #    \n"
                + "#       #####    #    #####   #  #      #  #      #    \n"
                + "#       #   #    #    #   #   #  #    # #   #          \n"
                + "#       #   #    #    #    #  #   ####  #    #    #    \n";

        System.out.println("Hello from\n" + logo);
        System.out.println("Hi, I'm Patrick star.");

        ArrayList<Task> tasks = loadTasks();  // Load tasks from file at startup

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Reads input until user types bye
        while (!input.toLowerCase().equals("bye")) {
            try {
                String[] parts = input.split(" ", 2);
                String command = parts[0].toLowerCase();

                if (command.equals("list")) {
                    System.out.println("Uhh... here are your tasks: ");
                    // Display all tasks added by users
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (command.equals("mark")) {
                    // Mark task as done
                    if (parts.length < 2) {
                        throw new DukeException("Uhhh... which task do I mark?");
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= tasks.size()) {
                        throw new DukeException("Huh? That task doesn't exist...");
                    }
                    tasks.get(taskNum).markAsDone();
                    System.out.println("Alright, yeah. I've marked this task as done:");
                    System.out.println(tasks.get(taskNum));
                    saveTasks(tasks);  // Save after modification
                } else if (command.equals("unmark")) {
                    // Unmark task
                    if (parts.length < 2) {
                        throw new DukeException("Uhhh... which task do I unmark?");
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= tasks.size()) {
                        throw new DukeException("Huh? That task doesn't exist...");
                    }
                    tasks.get(taskNum).markAsNotDone();
                    System.out.println("Alright I will unmark this task:");
                    System.out.println(tasks.get(taskNum));
                    saveTasks(tasks);  // Save after modification
                } else if (command.equals("delete")) {
                    // Delete task
                    if (parts.length < 2) {
                        throw new DukeException("Uhhh... which task do you want me to delete?");
                    }
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    if (taskNum < 0 || taskNum >= tasks.size()) {
                        throw new DukeException("Huh? That task doesn't exist...");
                    }
                    Task deletedTask = tasks.remove(taskNum);  // Remove and return the task
                    System.out.println("Alright yeah. I will remove this task:");
                    System.out.println("  " + deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);  // Save after modification
                } else if (command.equals("todo")) {
                    // Create ToDo Task
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new DukeException("Uhhh... What is the name of the ToDo task again?");
                    }
                    String description = parts[1];
                    tasks.add(new ToDo(description));
                    System.out.println("Alright. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);  // Save after modification
                } else if (command.equals("deadline")) {
                    // Create Deadline Task
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
                        tasks.add(new Deadline(description, by));
                        System.out.println("Alright. I've added this task:");
                        System.out.println(tasks.get(tasks.size() - 1));
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        saveTasks(tasks);  // Save after modification
                    } catch (DateTimeParseException e) {
                        throw new DukeException("Uhhh... I need the date in yyyy-MM-dd format (like 2019-10-15)");
                    }
                } else if (command.equals("event")) {
                    // Create Event task
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
                        tasks.add(new Event(description, from, to));
                        System.out.println("Alright. I've added this task:");
                        System.out.println(tasks.get(tasks.size() - 1));
                        System.out.println("Uhhh... Now you have " + tasks.size() + " tasks in the list.");
                        saveTasks(tasks);  // Save after modification
                    } catch (DateTimeParseException e) {
                        throw new DukeException("Uhhh... I need dates in yyyy-MM-dd HHmm format (like 2019-10-15 1800)");
                    }
                } else {
                    throw new DukeException("Uhhh... I don't understand what that means. Is mayonnaise a command?");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Uhhh... that date format doesn't look right...");
            } catch (NumberFormatException e) {
                System.out.println("Uhhh... that doesn't look like a number to me...");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("I think something's missing in your command");
            }

            input = scanner.nextLine();
        }

        System.out.println("What? Who you calling pinhead? Bye bye.");
        scanner.close();
    }
}