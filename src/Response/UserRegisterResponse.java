package Response;

/**
 * Holds the user Response Body
 */
public class UserRegisterResponse {

    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private boolean success;

    /**
     * A constructor to get the response of the success response body
     * @param username the username
     * @param authtoken the auth token associated with that username
     * @param personID the ID of the person
     * @param success success
     */
    public UserRegisterResponse(String username, String authtoken, String personID, boolean success){
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * A constructor to create an error response body
     * @param message the message of the error
     * @param success failure
     */
    public UserRegisterResponse(String message, boolean success){
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
        return personID;
    }

    public void setPersonsID(String personsID) {
        this.personID = personsID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {

        this.success = success;
    }

    public void setDataNull(){
        this.username = null;
        this.authtoken = null;
        this.personID = null;
    }
    public void setMessageNull(){
        this.message = null;
    }
}
