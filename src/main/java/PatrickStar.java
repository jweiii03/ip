import java.util.*;

public class PatrickStar {
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

        ArrayList<Task> tasks = new ArrayList<>();  // Use ArrayList for dynamic sizing

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
                    String by = deadlineParts[1];
                    tasks.add(new Deadline(description, by));
                    System.out.println("Alright. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
                    String from = eventParts[1];
                    String to = eventParts[2];
                    tasks.add(new Event(description, from, to));
                    System.out.println("Alright. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Uhhh... Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new DukeException("Uhhh... I don't understand what that means. Is mayonnaise a command?");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
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