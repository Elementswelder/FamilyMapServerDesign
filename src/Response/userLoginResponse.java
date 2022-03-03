package Response;

/**
 * Makes requests and responses for userLogin
 */
public class userLoginResponse {

    private String username, authtoken, personID;
    private String message;
    boolean success;

    /**
     * Create constructor to setup the Resposne body
     * @param authtoken the authtoken
     * @param username the username
     * @param personID the personID
     * @param success if it was successful or not
     */
    public userLoginResponse(String authtoken, String username, String personID, boolean success){
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * Secondary construtor if the request failed
     * @param message the error message
     * @param success the fail
     */
    public userLoginResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
