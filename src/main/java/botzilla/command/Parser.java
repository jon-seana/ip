package botzilla.command;
import botzilla.exception.BotzillaException;
import botzilla.storage.Storage;
import botzilla.task.Deadline;
import botzilla.task.Event;
import botzilla.task.TaskList;
import botzilla.task.Todo;
import botzilla.ui.Ui;

/**
 * Represents a class for the Parser constructor and parse method which takes in a String input and
 * selects the appropriate actions to be taken based on the type of input (e.g. todo, list, bye, etc...).
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    /**
     * The constructor for Parser class.
     *
     * @param taskList Tasklist.
     * @param storage Storage.
     * @param ui Ui.
     */
    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Choose a variety of actions depending on the String input. Returns a String output for that action.
     *
     * @param input Input command.
     * @return String.
     */
    public String parseString(String input) {
        String horizontalLine = "\t_____________________________________________________________________";
        String endFormat = horizontalLine + "\n" + " ";
        StringBuilder output = new StringBuilder();
        try {
            if (input.trim().equals("list")) {
                output.append(taskList.getTaskListString());
            } else if (input.trim().equals("bye")) {
                output.append(ui.sayGoodByeString());
            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                if (taskList.isEmpty()) {
                    output.append(ui.markUnmarkEmptyListString());
                } else {
                    taskList.markDone(index - 1);
                    storage.saveTask(taskList);
                    output.append("\t Nice! I've marked this task as done:")
                            .append("\n")
                            .append("\t   ")
                            .append(taskList.getTask().get(index - 1).toString());
                }
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                if (taskList.isEmpty()) {
                    output.append(ui.markUnmarkEmptyListString());
                } else {
                    taskList.markUndone(index - 1);
                    storage.saveTask(taskList);
                    output.append("\t OK, I've marked this task as not done yet:")
                            .append("\n")
                            .append("\t   ")
                            .append(taskList.getTask().get(index - 1).toString());
                }
            } else if (input.startsWith("todo")) {
                Todo createTodo = Todo.createTodo(input, ui);
                if (createTodo == null) {
                    return "\t Hi there! Please add a description for a todo task.";
                }
                taskList.addTask(createTodo);
                storage.saveTask(taskList);
                output.append(ui.getPrintOutString(taskList, createTodo));
            } else if (input.startsWith("deadline")) {
                Deadline createDeadline = Deadline.createDeadline(input, ui);
                if (createDeadline == null) {
                    return "\t Hi there! Please follow the format: deadline task /by d/mm/yyyy HHmm.";
                }
                taskList.addTask(createDeadline);
                storage.saveTask(taskList);
                output.append(ui.getPrintOutString(taskList, createDeadline));
            } else if (input.startsWith("event")) {
                Event createEvent = Event.createEvent(input, ui);
                if (createEvent == null) {
                    return "\t Hi there! Please follow the format: event task /from d/mm/yyyy HHmm /to d/mm/yyyy HHmm.";
                }
                taskList.addTask(createEvent);
                storage.saveTask(taskList);
                output.append(ui.getPrintOutString(taskList, createEvent));
            } else if (input.startsWith("delete")) {
                String deleted = taskList.deleteTask(input, ui);
                if (deleted == null) {
                    return "\t Hi there! Please enter a valid task number you want to delete." + "\n"
                            + "\t The task number you provided may have been removed or may not exist at all.";
                }
                storage.saveTask(taskList);
                output.append("\t Noted. I've removed this task:")
                        .append("\n")
                        .append("\t   ")
                        .append(deleted)
                        .append("\n")
                        .append("\t Now you have ")
                        .append(taskList.size())
                        .append(" tasks in the list.");
            } else if (input.startsWith("find")) {
                if (input.length() <= 5) {
                    throw new BotzillaException(horizontalLine + "\n"
                                                        + "\t Find command requires this format:" + "\n"
                                                        + "\t find <keyword(s)>" + "\n" + endFormat);
                }
                String keyword = input.substring(4);
                output.append(taskList.findTaskString(keyword));
            } else {
                output.append(ui.dontUnderstandString());
            }
        } catch (BotzillaException error) {
            output.append(error.getMessage());
        }
        return output.toString();
    }

    /**
     * Choose a variety of actions depending on the String input.
     *
     * @param input Input command.
     */
    public void parse(String input) {
        String horizontalLine = "\t_____________________________________________________________________";
        String endFormat = horizontalLine + "\n" + " ";
        try {
            if (input.trim().equals("list")) {
                taskList.listTask();
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
                // maybe should put this system.out.println in the else block!!
                System.out.println(horizontalLine);
                System.out.println("\t Nice! I've marked this task as done:");
                System.out.println("\t   " + taskList.getTask().get(index - 1).toString());
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
                System.out.println("\t   " + taskList.getTask().get(index - 1).toString());
                System.out.println(endFormat);
            } else if (input.startsWith("todo")) {
                Todo createToDo = Todo.createTodo(input, ui);
                if (createToDo == null) {
                    return;
                }
                ui.printOut(taskList, createToDo);
            } else if (input.startsWith("deadline")) {
                Deadline createDeadline = Deadline.createDeadline(input, ui);
                if (createDeadline == null) {
                    return;
                }
                ui.printOut(taskList, createDeadline);
            } else if (input.startsWith("event")) {
                Event createEvent = Event.createEvent(input, ui);
                if (createEvent == null) {
                    return;
                }
                ui.printOut(taskList, createEvent);
            } else if (input.startsWith("delete")) {
                String deleted = taskList.deleteTask(input, ui);
                if (deleted == null) {
                    return;
                }
                storage.saveTask(taskList);
                System.out.println(horizontalLine);
                System.out.println("\t Noted. I've removed this task:");
                System.out.println("\t   " + deleted);
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (input.startsWith("find")) {
                if (input.length() <= 5) {
                    throw new BotzillaException(horizontalLine + "\n"
                                                        + "\t Find command requires this format:" + "\n"
                                                        + "\t find <keyword(s)>" + "\n" + endFormat);
                }
                String keyword = input.substring(4);
                taskList.findTask(keyword, ui);
            } else {
                ui.dontUnderstand();
            }
        } catch (BotzillaException error) {
            ui.showErrorMessage(error.getMessage());
        }
    }
}
