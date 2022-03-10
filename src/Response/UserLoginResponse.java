package Response;

/**
 * Makes requests and responses for userLogin
 */
public class UserLoginResponse {

    private String username;
    private String authtoken;
    private String personID;
    private String message;
    boolean success;

    /**
     * Create constructor to set up the Response body
     * @param authtoken the authtoken
     * @param username the username
     * @param personID the personID
     * @param success if it was successful or not
     */
    public UserLoginResponse(String authtoken, String username, String personID, boolean success){
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * Secondary constructor if the request failed
     * @param message the error message
     * @param success the fail
     */
    public UserLoginResponse(String message, boolean success){
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

    public void setDataNull(){
        this.authtoken = null;
        this.username = null;
        this.personID = null;
    }

    public void setMessageNull(){
        this.message = null;
    }
}
