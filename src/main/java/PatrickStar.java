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

        Task[] tasks = new Task[100];  // Create list of Tasks instead of String list
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Reads input until user types bye
        while (!input.toLowerCase().equals("bye")) {
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();

            if (command.equals("list")) {
                System.out.println("Uhh... here are your tasks: ");
                // Display all tasks added by users
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.equals("mark")) {
                // Mark task as done
                int taskNum = Integer.parseInt(parts[1]) - 1;
                tasks[taskNum].markAsDone();
                System.out.println("Alright, yeah. I've marked this task as done:");
                System.out.println(tasks[taskNum]);
            } else if (command.equals("unmark")) {
                // Unmark task
                int taskNum = Integer.parseInt(parts[1]) - 1;
                tasks[taskNum].markAsNotDone();
                System.out.println("Huh? Alright I will unmark this task:");
                System.out.println(tasks[taskNum]);
            } else if (command.equals("todo")) {
                // Create ToDo Task
                String description = parts[1];
                tasks[taskCount] = new ToDo(description);
                System.out.println("Alright. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (command.equals("deadline")) {
                // Create Deadline Task
                String[] deadlineParts = parts[1].split(" /by ");
                String description = deadlineParts[0];
                String by = deadlineParts[1];
                tasks[taskCount] = new Deadline(description, by);
                System.out.println("Alright. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (command.equals("event")) {
                // Create Event task
                String[] eventParts = parts[1].split(" /from | /to ");
                String description = eventParts[0];
                String from = eventParts[1];
                String to = eventParts[2];
                tasks[taskCount] = new Event(description, from, to);
                System.out.println("Alright. I've added this task:");
                System.out.println(tasks[taskCount]);
                taskCount++;
                System.out.println("Uhhh... Now you have " + taskCount + " tasks in the list.");
            }

            input = scanner.nextLine();
        }

        System.out.println("Uhhh... Is mayonnaise an instrument? Bye bye.");
        scanner.close();
    }
}
