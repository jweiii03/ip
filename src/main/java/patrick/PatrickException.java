package patrick;

/**
 * Represents an exception specific to Duke/Patrick application errors
 */
public class PatrickException extends Exception {
    /**
     * Creates a new DukeException with the specified error message
     *
     * @param message The error message
     */
    public PatrickException(String message) {
        super(message);
    }
}
