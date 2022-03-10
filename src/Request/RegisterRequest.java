package Request;

/**
 * Holds the Request Body Data
 */
public class RegisterRequest {

    private String username, password, email, firstName, lastName, gender;


    /**
     * A constructor to fill in the request body
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param gender the gender of the user
     */
    public RegisterRequest(String username, String password, String email, String firstName,
                           String lastName, String gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
