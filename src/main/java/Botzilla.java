
public class Botzilla {
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;
    private static Parser parser;

    public Botzilla() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.loadTask());
        } catch (BotzillaException error) {
            ui.showErrorMessage("unable to load tasks!!");
            tasks = new TaskList();
        }
        parser = new Parser(tasks, storage, ui);
    }

    public void run() {
        ui.showGreeting();
        while (true) {
            String input = ui.readLine();
            parser.parse(input);
        }
    }

    public static void main(String[] args) {
        new Botzilla().run();
    }
}