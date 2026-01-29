package patrick;

import java.util.ArrayList;
import patrick.task.*;

// Contains the task list and operations to add/delete tasks in the list.

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list
     * @param task The task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list
     * @param index The index of the task to delete (0-based)
     * @return The deleted task
     * @throws DukeException if the index is invalid
     */
    public Task deleteTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Huh? That task doesn't exist...");
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task from the list
     * @param index The index of the task (0-based)
     * @return The task at the specified index
     * @throws DukeException if the index is invalid
     */
    public Task getTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Huh? That task doesn't exist...");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     * @param index The index of the task to mark (0-based)
     * @throws DukeException if the index is invalid
     */
    public void markTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Huh? That task doesn't exist...");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task (marks as not done)
     * @param index The index of the task to unmark (0-based)
     * @throws DukeException if the index is invalid
     */
    public void unmarkTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Huh? That task doesn't exist...");
        }
        tasks.get(index).markAsNotDone();
    }

    /**
     * Gets the number of tasks in the list
     * @return The number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets the ArrayList of tasks
     * @return The ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     * @param keyword The keyword to search for
     * @return ArrayList of tasks that match the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}