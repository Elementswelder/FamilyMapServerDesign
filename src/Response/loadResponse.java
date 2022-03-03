package Response;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Clears then loads all the information into the database
 */
public class loadResponse {

    private String message;
    boolean validate;

    /**
     * Constructor to get the messages of error of the response body
     * @param message The message of what happened
     * @param success if it was successful or not
     */
    public loadResponse(String message, boolean success) {
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
