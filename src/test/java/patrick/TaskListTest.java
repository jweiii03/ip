package patrick;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import patrick.task.ToDo;
import patrick.task.Task;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void addTask_addsTaskSuccessfully() {
        Task task = new ToDo("read book");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_validIndex_deletesTask() throws DukeException {
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);

        Task deleted = taskList.deleteTask(0);

        assertEquals(task1, deleted);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_invalidIndex_throwsException() {
        assertThrows(DukeException.class, () -> {
            taskList.deleteTask(0);
        });
    }

    @Test
    public void markTask_validIndex_marksTaskAsDone() throws DukeException {
        Task task = new ToDo("read book");
        taskList.addTask(task);

        taskList.markTask(0);

        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void markTask_invalidIndex_throwsException() {
        assertThrows(DukeException.class, () -> {
            taskList.markTask(5);
        });
    }

    @Test
    public void unmarkTask_validIndex_unmarksTask() throws DukeException {
        Task task = new ToDo("read book");
        taskList.addTask(task);
        taskList.markTask(0);

        taskList.unmarkTask(0);

        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void getTask_validIndex_returnsTask() throws DukeException {
        Task task = new ToDo("read book");
        taskList.addTask(task);

        Task retrieved = taskList.getTask(0);

        assertEquals(task, retrieved);
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(DukeException.class, () -> {
            taskList.getTask(0);
        });
    }
}
