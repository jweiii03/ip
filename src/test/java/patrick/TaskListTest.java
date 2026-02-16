package patrick;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import patrick.task.Task;
import patrick.task.ToDo;

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
    public void deleteTask_validIndex_deletesTask() throws PatrickException {
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
        assertThrows(PatrickException.class, () -> {
            taskList.deleteTask(0);
        });
    }

    @Test
    public void markTask_validIndex_marksTaskAsDone() throws PatrickException {
        Task task = new ToDo("read book");
        taskList.addTask(task);

        taskList.markTask(0);

        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void markTask_invalidIndex_throwsException() {
        assertThrows(PatrickException.class, () -> {
            taskList.markTask(5);
        });
    }

    @Test
    public void unmarkTask_validIndex_unmarksTask() throws PatrickException {
        Task task = new ToDo("read book");
        taskList.addTask(task);
        taskList.markTask(0);

        taskList.unmarkTask(0);

        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void getTask_validIndex_returnsTask() throws PatrickException {
        Task task = new ToDo("read book");
        taskList.addTask(task);

        Task retrieved = taskList.getTask(0);

        assertEquals(task, retrieved);
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(PatrickException.class, () -> {
            taskList.getTask(0);
        });
    }
}
