package patrick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import patrick.task.ToDo;

public class ToDoTest {

    @Test
    public void toString_unmarkedToDo_correctFormat() {
        ToDo todo = new ToDo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toString_markedToDo_correctFormat() {
        ToDo todo = new ToDo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void toFileFormat_unmarkedToDo_correctFormat() {
        ToDo todo = new ToDo("buy groceries");
        assertEquals("T | 0 | buy groceries", todo.toFileFormat());
    }

    @Test
    public void toFileFormat_markedToDo_correctFormat() {
        ToDo todo = new ToDo("buy groceries");
        todo.markAsDone();
        assertEquals("T | 1 | buy groceries", todo.toFileFormat());
    }

    @Test
    public void markAsDone_changesStatus() {
        ToDo todo = new ToDo("test task");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void markAsNotDone_changesStatus() {
        ToDo todo = new ToDo("test task");
        todo.markAsDone();
        todo.markAsNotDone();
        assertEquals(" ", todo.getStatusIcon());
    }
}