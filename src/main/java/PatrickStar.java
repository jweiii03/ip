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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Reads input until user types bye
        while (!input.toLowerCase().equals("bye")) {
            System.out.println("Uhhh...uhh..." + input);  // Echo user's input
            input = scanner.nextLine();  // Read next inputs
        }

        System.out.println("Uhhh... Is mayonnaise an instrument? Bye bye.");
        scanner.close();
    }
}
