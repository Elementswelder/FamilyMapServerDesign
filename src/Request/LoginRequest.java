package Request;

/**
 * Makes requests and responses for userLogin
 */
public class LoginRequest {

    private String username;
    private String password;

    /**
     * Constructor to get the request body
     * @param username the username
     * @param password the password
     */
    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }
}
