package patrick;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import patrick.task.*;


/**
 * Handles loading tasks from file and saving tasks to file
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * @return ArrayList of tasks loaded from file
     * @throws DukeException if there's an error loading the file
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
                    Task task = parseTaskFromFile(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Skip corrupted lines
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Uhhh... I couldn't find the file to load tasks.");
        }

        return tasks;
    }

    /**
     * Saves tasks to the file.
     * @param tasks The list of tasks to save
     * @throws DukeException if there's an error saving the file
     */
    public void save(ArrayList<Task> tasks) throws DukeException {
        try {
            // Create directory if it doesn't exist
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());

            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException("Uhhh... I couldn't save the tasks. Sorry!");
        }
    }

    /**
     * Parses a task from file format.
     * @param line The line from the file to parse
     * @return The parsed Task object
     */
    private Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null; // Invalid format
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        try {
            if (type.equals("T")) {
                task = new ToDo(description);
            } else if (type.equals("D") && parts.length >= 4) {
                LocalDate by = LocalDate.parse(parts[3], DateTimeFormatter.ISO_LOCAL_DATE);
                task = new Deadline(description, by);
            } else if (type.equals("E") && parts.length >= 5) {
                LocalDateTime from = LocalDateTime.parse(parts[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime to = LocalDateTime.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                task = new Event(description, from, to);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Uhhh... Theres a error parsing date from file: " + line);
            return null;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }
}