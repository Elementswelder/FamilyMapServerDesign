package Response;

/**
 * Clears then loads all the information into the database
 */
public class LoadResponse {

    private String message;
    boolean success;

    /**
     * Constructor to get the messages of error of the response body
     * @param message The message of what happened
     * @param success if it was successful or not
     */
    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValidate() {
        return success;
    }
}
