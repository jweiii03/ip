package patrick;

import java.util.ArrayList;

import patrick.task.Task;

/**
 * Contains the task list and operations to add/delete tasks in the list
 */
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
        assert task != null : "Task to be added cannot be null";
        tasks.add(task);
    }

    /**
     * Validates that a task index is within valid bounds.
     * @param index The index to validate (0-based)
     * @throws PatrickException if the index is invalid
     */
    private void validateTaskIndex(int index) throws PatrickException {
        if (index < 0 || index >= tasks.size()) {
            throw new PatrickException("Huh? That task doesn't exist...");
        }
    }

    /**
     * Deletes a task from the list
     * @param index The index of the task to delete (0-based)
     * @return The deleted task
     * @throws PatrickException if the index is invalid
     */
    public Task deleteTask(int index) throws PatrickException {
        validateTaskIndex(index);

        Task deletedTask = tasks.remove(index);
        assert deletedTask != null : "Deleted task should not be null";
        return deletedTask;
    }

    /**
     * Gets a task from the list
     * @param index The index of the task (0-based)
     * @return The task at the specified index
     * @throws PatrickException if the index is invalid
     */
    public Task getTask(int index) throws PatrickException {
        validateTaskIndex(index);
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null for valid index";
        return task;
    }

    /**
     * Marks a task as done.
     * @param index The index of the task to mark (0-based)
     * @throws PatrickException if the index is invalid
     */
    public void markTask(int index) throws PatrickException {
        validateTaskIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task (marks as not done)
     * @param index The index of the task to unmark (0-based)
     * @throws PatrickException if the index is invalid
     */
    public void unmarkTask(int index) throws PatrickException {
        validateTaskIndex(index);
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
