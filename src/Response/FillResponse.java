package Response;

/**
 * Fills users family tree with certain up to 4 generations
 */
public class FillResponse {

    private String message;
    boolean success;

    /**
     * Construtor to setup the response body
     * @param message the message of what happened
     * @param success if it was sueccessful or not
     */
    public FillResponse(String message, boolean success){
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
