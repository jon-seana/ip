package botzilla.storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import botzilla.exception.BotzillaException;
import botzilla.task.Deadline;
import botzilla.task.Event;
import botzilla.task.Task;
import botzilla.task.TaskList;
import botzilla.task.Todo;

/**
 * Represents the class for loading and saving tasks set by user.
 * Also checks whether the output file exists, else it will create a file for it.
 */
public class Storage {
    private static final String FILE_PATH = "src/main/tasks.txt";
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Ensures that the file exists in the computer's local hard disk.
     * If the file does not exist, it will create a new file.
     *
     * @throws BotzillaException If there is an error creating the new file.
     */
    private static void ensureFileExist() throws BotzillaException {
        File file = new File(Storage.FILE_PATH);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            assert file.exists() : "File is not readable at " + FILE_PATH;
        } catch (IOException e) {
            throw new BotzillaException("Error creating file!");
        }
    }

    /**
     * Loads task from the tasks.txt file which is saved in the hard disk of the computer.
     *
     * @return ArrayList (Type: Task) Array of tasks stored in an arraylist.
     * @throws BotzillaException If there is an error loading the tasks from the file.
     */
    public ArrayList<Task> loadTask() throws BotzillaException {
        ensureFileExist();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                processTaskLine(line);
            }
        } catch (IOException error) {
            throw new BotzillaException("Error loading tasks from file!");
        }
        return tasks;
    }

    /**
     * Loads the task list line by line and processes it according to task type.
     *
     * @param line Task line.
     */
    private void processTaskLine(String line) {
        assert !line.trim().isEmpty() : "Encountered empty line in task file!";
        String[] parts = line.split(" ");
        String type = parts[0].substring(1, 2);
        boolean isDone = parts[0].length() > 4 && parts[0].substring(4, 5).equals("X");

        switch (type) {
        case "T":
            tasks.add(createTodoFromLine(line, isDone));
            break;
        case "D":
            tasks.add(createDeadlineFromLine(line, isDone));
            break;
        case "E":
            tasks.add(createEventFromLine(line, isDone));
            break;
        default:
            break;
        }
    }

    /**
     * Creates a new Todo task from the given input.
     *
     * @param line The task line from the file.
     * @param isDone The status of the task from the file.
     * @return Todo.
     */
    private Todo createTodoFromLine(String line, boolean isDone) {
        String description = line.substring(7).trim();
        Todo toDo = new Todo(description);
        if (isDone) {
            toDo.markAsDone();
        }
        return toDo;
    }

    /**
     * Creates a new Deadline task from the given input.
     *
     * @param line The task line from the file.
     * @param isDone The status of the task from the file.
     * @return Deadline.
     */
    private Deadline createDeadlineFromLine(String line, boolean isDone) {
        String description = line.substring(7, line.indexOf("(by:")).trim();
        String date = line.substring(line.indexOf(":") + 2, line.indexOf(")")).trim();
        Deadline deadline = new Deadline(description, date);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    /**
     * Creates a new Event task from the given input.
     *
     * @param line The task line from the file.
     * @param isDone The status of the task from the file.
     * @return Event.
     */
    private Event createEventFromLine(String line, boolean isDone) {
        String description = line.substring(7, line.indexOf("(from:")).trim();
        String from = line.substring(line.indexOf("m:") + 3, line.indexOf("to:")).trim();
        String to = line.substring(line.indexOf("o:") + 3, line.indexOf(")")).trim();
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }

    /**
     * Saves tasks when it is taken in as a parameter.
     * Task will be saved to the computer's local hard disk.
     *
     * @param tasks Tasks input by user.
     * @throws BotzillaException If there is an error saving the tasks to the file.
     */
    public void saveTask(TaskList tasks) throws BotzillaException {
        assert tasks != null : "Task should not be null in saveTask() method";
        ArrayList<Task> taskList = tasks.getTask();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : taskList) {
                assert task != null : "Encountered null task while saving";
                writer.write(task + "\n");
            }
            writer.close();
        } catch (IOException error) {
            throw new BotzillaException("Error saving tasks to file!");
        }
    }
}
