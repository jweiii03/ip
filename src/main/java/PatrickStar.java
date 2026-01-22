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

        String[] tasks = new String[100]; // Store user's task
        int taskCount = 0;  // Track number of tasks
        boolean[] isDone = new boolean[100];  // Track done status of tasks

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Reads input until user types bye
        while (!input.toLowerCase().equals("bye")) {
            if (input.toLowerCase().equals("list")) {
                System.out.println("Uhh... here are your tasks: ");
                // Display all tasks added by users
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println((i + 1) + "." + status + " " + tasks[i]);
                }
            } else if (input.toLowerCase().startsWith("mark ")) {
                // Mark task as done
                int taskNum = Integer.parseInt(input.substring(5)) - 1;
                isDone[taskNum] = true;
                System.out.println("Alright, yeah. I've marked this task as done:");
                System.out.println("[X] " + tasks[taskNum]);
            } else {
                // Add task
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("Alright, yeah. I added: " + input);
            }
            input = scanner.nextLine();
        }

        System.out.println("Uhhh... Is mayonnaise an instrument? Bye bye.");
        scanner.close();
    }
}
