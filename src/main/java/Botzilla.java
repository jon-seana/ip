import java.util.Scanner;

public class Botzilla {
    public static void main(String[] args) {
        System.out.println("\t_____________________________________________________________________");
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println("\t_____________________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        String[] list = new String[100];
        int a = 0;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("list")) {
                System.out.println("\t_____________________________________________________________________");
                int lengthOfList = list.length;
                for (int i = 0; i < lengthOfList; i++) {
                    if (list[i] != null) {
                        int b = i + 1;
                        System.out.println("\t " + b + ". " + list[i]);
                    }
                }
                System.out.println("\t_____________________________________________________________________");
            } else if (input.equals("bye")) {
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println("\t_____________________________________________________________________");
                break;
            } else {
                list[a++] = input;
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t " + "added: " + input);
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            }
        }

        scanner.close();
    }
}

