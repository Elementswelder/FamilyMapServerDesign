package Response;

/**
 * The Class that will call all DOAs to clear
 */
public class ClearResponse {

    private String message;
    private boolean success;

    /**
     * Setup with the message and success
     * @param message the message of what happened
     * @param success if it was successful or not
     */
    public ClearResponse(String message, boolean success){
        this.message = message;
        this.success = success;

    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
