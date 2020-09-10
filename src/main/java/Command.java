import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Command {
    /**
     * Prints out the current tasks in the list
     * @param command
     * @param list
     * @param storage
     * @return List of current tasks in String format
     * @throws DukeException
     * */
    public String list(String command, TaskList list, ArrayList<Task> storage) throws DukeException {
        if (list.isEmpty()) {
            throw new DukeException(command);
        }
        String tasks = "";
        for (Task item : storage) {
            int position = storage.indexOf(item) + 1;
            tasks = tasks + position + "." + item + "\n";
        }
        return tasks;
    }

    /**
     * Checks a particular task in the list as done
     * @param command
     * @param store
     * @param storage
     * @return Selected Task to mark done in String format
     * @throws DukeException
     */
    public String done(String command, Storage store, ArrayList<Task> storage) throws DukeException {
        try {
            int number = Integer.parseInt(command.split(" ")[1]);
            Task current = storage.get(number - 1);
            current.setDone();
            store.save(storage);
            return "Nice! I've marked this task as done: " + "\n"
                    + current.getStatusIcon() + " " + current.getDescription();
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("delete2");
        } catch (InputMismatchException e) {
            throw new DukeException(command);
        } catch (FileNotFoundException e) {
            return "Sorry, the list is currently empty!";
        }
    }

    /**
     * Adds a Todo Task into the current list of Tasks
     * @param command
     * @param store
     * @param storage
     * @return a Todo Task in String format
     * @throws DukeException
     * @throws FileNotFoundException
     */
    public String todo(String command, Storage store, ArrayList<Task> storage)
            throws DukeException, FileNotFoundException {
        if (command.equals("todo ")) {
            throw new DukeException(command);
        }
        String[] string = command.split("do ");
        if (string.length < 2) {
            throw new DukeException("deadline");
        }
        String desc = string[1];
        Todo todo = new Todo(desc);

        store.save(storage, todo);
        int size = storage.size();
        return "Got it. I've added this task: " + "\n" + todo + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Adds a Deadline Task into the current list of Tasks
     * @param command
     * @param store
     * @param storage
     * @return a Deadline Task with the time in String format
     * @throws DukeException
     * @throws FileNotFoundException
     */
    public String deadline(String command, Storage store, ArrayList<Task> storage)
            throws DukeException, FileNotFoundException {
        if (command.equals("deadline ")) {
            throw new DukeException("deadline");
        }
        String[] string = command.split("/by ");
        if (string.length < 2) {
            throw new DukeException("deadline");
        }
        Deadline deadline = new Deadline(string[0], string[1]);
        store.save(storage, deadline);
        int size = storage.size();
        return "Got it. I've added this task: " + "\n" + deadline + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Adds an Event Task to the current list of Tasks
     * @param command
     * @param store
     * @param storage
     * @return an Event Task with the time in String format
     * @throws DukeException
     * @throws FileNotFoundException
     */
    public String event(String command, Storage store, ArrayList<Task> storage)
            throws DukeException, FileNotFoundException {
        if (command.equals("event ")) {
            throw new DukeException("event");
        }
        String[] string = command.split("/at ");
        if (string.length < 2) {
            throw new DukeException("event");
        }
        Events event = new Events(string[0], string[1]);
        store.save(storage, event);
        int size = storage.size();
        return "Got it. I've added this task: " + "\n" + event + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Prints the good bye message
     * @return the good bye message in String format
     */
    public String bye() {
        String bye = "Bye. Hope to see you again soon!";
        return bye;
    }

    /**
     * Deletes an item of choice from the current list of Tasks
     * @param command
     * @param store
     * @param storage
     * @return the selected deleted item in String format
     * @throws DukeException
     */
    public String delete(String command, Storage store, ArrayList<Task> storage) throws DukeException {
        try {
            int number = Integer.parseInt(command.split(" ")[1]);
            Task task = storage.get(number - 1);
            storage.remove(number - 1);
            store.save(storage);
            int size = storage.size();
            return "Noted, I've removed this task: " + "\n" + task + "\n"
                    + "Now you have " + size + " tasks in the list.";
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("delete2");
        } catch (InputMismatchException | FileNotFoundException e) {
            throw new DukeException(command);
        }
    }

    /**
     * Searches all relevant Tasks in the current list based on
     * the keyword provided
     * @param command
     * @param storage
     * @return relevant Tasks based on the keyword in String format
     * @throws DukeException
     * */
    public String find(String command, ArrayList<Task> storage) throws DukeException {
        String[] desc = command.split("find");
        if (desc.length < 2) {
            throw new DukeException(command);
        }
        String keyword = command.split("nd ")[1];

        int count = 0;
        String tasks = "";
        for (Task item : storage) {
            if (item.getDescription().contains(keyword)) {
                count++;
                tasks = tasks + count + "." + item + "\n";
            }
        }
        if (tasks.equals("")) {
            throw new DukeException("find2");
        } else {
            return tasks;
        }
    }
}