import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    public void parse(String input) {
        String taskFirstLine = "\t Got it. I've added this task:";
        String horizontalLine = "\t_____________________________________________________________________";
        String endFormat = horizontalLine + "\n" + " ";
        try {
            if (input.trim().equals("list")) {
                taskList.listTasks();
            } else if (input.trim().equals("bye")) {
                ui.sayGoodBye();
                System.exit(0);
            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                if (taskList.isEmpty()) {
                    ui.markUnmarkEmptyList();
                } else {
                    taskList.markDone(index - 1);
                    storage.saveTask(taskList);
                }
                System.out.println(horizontalLine);
                System.out.println("\t Nice! I've marked this task as done:");
                System.out.println("\t   " + taskList.getTasks().get(index - 1).toString());
                System.out.println(endFormat);
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                if (taskList.isEmpty()) {
                    ui.markUnmarkEmptyList();
                } else {
                    taskList.markUndone(index - 1);
                    storage.saveTask(taskList);
                }
                System.out.println(horizontalLine);
                System.out.println("\t OK, I've marked this task as not done yet:");
                System.out.println("\t   " + taskList.getTasks().get(index - 1).toString());
                System.out.println(endFormat);
            } else if (input.startsWith("todo")) {
                try {
                    if (input.substring(5).trim().isEmpty()) {
                        ui.toDoIncomplete();
                    } else {
                        taskList.addTask(new Todo(input.substring(5)));
                        storage.saveTask(taskList);
                    }
                } catch (IndexOutOfBoundsException e) {
                    ui.toDoError();
                    return;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.getTasks().get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (input.startsWith("deadline")) {
                try {
                    String[] deadlineInput = input.split(" /by ");
                    String description = deadlineInput[0].substring(9).trim();
                    String date = deadlineInput[1].trim();
                    LocalDateTime byDate;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");

                    if (date.contains("/")) {
                        byDate = LocalDateTime.parse(date, formatter);
                    } else if (date.contains("-")) {
                        byDate = LocalDateTime.parse(date, formatter2);
                    } else {
                        throw new DateTimeParseException("Invalid date format", date, 0);
                    }

                    taskList.addTask(new Deadline(description, byDate));
                    storage.saveTask(taskList);
                } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                    ui.deadLineParse();
                    return;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.getTasks().get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (input.startsWith("event")) {
                try {
                    String[] eventInput = input.split(" /from ");
                    String description = eventInput[0].substring(6);
                    String from = (eventInput[1].split(" /to "))[0].trim();
                    String to = (eventInput[1].split(" /to "))[1].trim();
                    LocalDateTime fromDate;
                    LocalDateTime toDate;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");

                    if (from.contains("/")) {
                        fromDate = LocalDateTime.parse(from, formatter);
                    } else if (from.contains("-")) {
                        fromDate = LocalDateTime.parse(from, formatter2);
                    } else {
                        throw new DateTimeParseException("Invalid date format", from, 0);
                    }

                    if (to.contains("/")) {
                        toDate = LocalDateTime.parse(to, formatter);
                    } else if (to.contains("-")) {
                        toDate = LocalDateTime.parse(to, formatter2);
                    } else {
                        throw new DateTimeParseException("Invalid date format", to, 0);
                    }

                    taskList.addTask(new Event(description, fromDate, toDate));
                    storage.saveTask(taskList);
                } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                    ui.eventParse();
                    return;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.getTasks().get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (input.startsWith("delete")) {
                String deleted;
                try {
                    int index = Integer.parseInt(input.split(" ")[1]);
                    deleted = taskList.getTasks().get(index - 1).toString();
                    taskList.deleteTask(index - 1);
                    storage.saveTask(taskList);
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.deleteError();
                    return;
                }
                System.out.println(horizontalLine);
                System.out.println("\t Noted. I've removed this task:");
                System.out.println("\t   " + deleted);
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else {
                ui.dontUnderstand();
            }
        } catch (BotzillaException error) {
            ui.showErrorMessage(error.getMessage());
        }
    }
}