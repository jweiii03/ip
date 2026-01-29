package patrick;

/**
 * Represents an exception specific to Duke/Patrick application errors
 */
public class DukeException extends Exception {
    /**
     * Creates a new DukeException with the specified error message
     *
     * @param message The error message
     */
    public DukeException(String message) {
        super(message);
    }
}
