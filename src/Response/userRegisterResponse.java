package Response;

/**
 * Holds the user Response Body
 */
public class userRegisterResponse {

    private String username, authtoken, personsID;
    private String message;
    private boolean success;

    /**
     * A construtor to get the repsonse of the success response body
     * @param username the username
     * @param authtoken the auth token associated with that username
     * @param personID the ID of the person
     * @param success success
     */
    public userRegisterResponse(String username, String authtoken, String personID, boolean success){
        this.username = username;
        this.authtoken = authtoken;
        this.personsID = personID;
        this.success = success;
    }

    /**
     * A constructor to create an error reponse body
     * @param message the message of the error
     * @param success failure
     */
    public userRegisterResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonsID() {
        return personsID;
    }

    public void setPersonsID(String personsID) {
        this.personsID = personsID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
