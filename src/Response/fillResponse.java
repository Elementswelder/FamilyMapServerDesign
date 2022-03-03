package Response;

/**
 * Fills users family tree with certain up to 4 generations
 */
public class fillResponse {

    private String message;
    boolean validate;

    /**
     * Construtor to setup the response body
     * @param message the message of what happened
     * @param success if it was sueccessful or not
     */
    public fillResponse(String message, boolean success){
        this.message = message;
        this.validate = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValidate() {
        return validate;
    }
}
