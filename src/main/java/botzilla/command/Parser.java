package botzilla.command;

import botzilla.storage.Storage;
import botzilla.task.TaskList;
import botzilla.ui.Ui;
import botzilla.task.Todo;
import botzilla.task.Deadline;
import botzilla.task.Event;
import botzilla.exception.BotzillaException;

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
     *  Choose a variety of actions depending on the String input.
     *
     * @param input Input command.
     */
    public void parse(String input) {
        String taskFirstLine = "\t Got it. I've added this task:";
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
            } else {
                ui.dontUnderstand();
            }
        } catch (BotzillaException error) {
            ui.showErrorMessage(error.getMessage());
        }
    }
}