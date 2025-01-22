import java.util.Scanner;

public class Botzilla {
    public static void main(String[] args) {
        System.out.println("\t_____________________________________________________________________");
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println("\t_____________________________________________________________________");

        Scanner scanner = new Scanner(System.in);


        while (true) {
            String input = scanner.nextLine();

            if (!input.equals("bye")) {
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t " + input);
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            } else {
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println("\t_____________________________________________________________________");
                break;
            }
        }

        scanner.close();
    }
}

