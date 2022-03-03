package Request;

/**
 * Makes requests and responses for userLogin
 */
public class userLoginRequest {

    private String username, password;

    /**
     * Constructor to get the request body
     * @param username the username
     * @param password the password
     */
    public userLoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
