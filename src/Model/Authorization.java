package Model;

/**
 * Have getters and setters based on the Authorization Object and verifies the Authorization
 */
public class Authorization {
    private String username;
    private String authToken;

    /**
     * Sets up the authorization object
     * @param username the username for the auth
     * @param authToken the token that is created for that username
     */
    public Authorization(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
