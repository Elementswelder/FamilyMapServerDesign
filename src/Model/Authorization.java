package Model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        Authorization e = (Authorization) o;
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        //Is O null?
        //Is o == this?
        //do this and o have the same class?

        //do this and o have the same wordCount and nodeCount?
        if (!Objects.equals(e.authToken, this.authToken)) {
            return false;
        }
        if (!Objects.equals(e.username, this.username)) {
            return false;

        }
        return true;
    }
}
