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

        String[] tasks = new String[100];
        int taskCount = 0;  // Track number of tasks

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Reads input until user types bye
        while (!input.toLowerCase().equals("bye")) {
            if (input.toLowerCase().equals("list")) {
                System.out.println("Uhh... here are your tasks: ");
                // Display all tasks added by users
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
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
