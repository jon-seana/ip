package botzilla.storage;

import botzilla.task.Task;
import botzilla.task.TaskList;
import botzilla.exception.BotzillaException;
import botzilla.task.Todo;
import botzilla.task.Deadline;
import botzilla.task.Event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the class for loading and saving tasks set by user.
 * Also checks whether the output file exists, else it will create a file for it.
 */
public class Storage {
    private static final String FILE_PATH = "src/main/tasks.txt";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void ensureFileExist() {
        File file = new File(Storage.FILE_PATH);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
        }
    }

    /**
     * Loads task from the tasks.txt file which is saved in the hard disk of the computer.
     *
     * @return ArrayList<Task> Array of tasks stored in an arraylist.
     * @throws BotzillaException Custom exception created for botzilla class.
     */
    public ArrayList<Task> loadTask() throws BotzillaException {
        ensureFileExist();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String type = parts[0].substring(1, 2);
                boolean isDone = parts[0].length() > 4 && parts[0].substring(4, 5).equals("X");

                if (type.equals("T")) {
                    String description = line.substring(7).trim();
                    Todo toDo = new Todo(description);
                    if (isDone) {
                        toDo.markAsDone();
                    }
                    tasks.add(toDo);
                } else if (type.equals("D")) {
                    String description = line.substring(7, line.indexOf("(by:")).trim();
                    String date = line.substring(line.indexOf(":") + 2, line.indexOf(")")).trim();
                    Deadline deadline = new Deadline(description, date);
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    tasks.add(deadline);
                } else if (type.equals("E")) {
                    String description = line.substring(7, line.indexOf("(from:")).trim();
                    String from = line.substring(line.indexOf("m:") + 3, line.indexOf("to:")).trim();
                    String to = line.substring(line.indexOf("o:") + 3, line.indexOf(")")).trim();
                    Event event = new Event(description, from, to);
                    if (isDone) {
                        event.markAsDone();
                    }
                    tasks.add(event);
                }
            }
        } catch (IOException error) {
            System.out.println("No tasks found!");
        }

        return tasks;
    }

    /**
     * Saves tasks when it is taken in as a parameter.
     * Task will be saved to the computer's local hard disk.
     *
     * @param tasks Tasks.
     */
    public void saveTask(TaskList tasks) {
        ArrayList<Task> taskList = tasks.getTask();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : taskList) {
                writer.write(task.saveData() + "\n");
            }
            writer.close();
        } catch (IOException error) {
            System.out.println("Error Occurred! Trying again...");
        }
    }
}
