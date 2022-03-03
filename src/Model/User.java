package Model;
//User Getter and Settters

import java.util.Objects;

/**
 * User Class that will set and get user functions based on the Table data
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * A Constructor to set the User object up
     * @param username user's username
     * @param password user's password
     * @param email user's email
     * @param firstName user's firstName
     * @param lastName user's lastName
     * @param gender user's gender
     * @param personID the personID for the user
     */
    public User(String username, String password, String email, String firstName,
                String lastName, String gender, String personID){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Override the equals method to go through a User Object
     * @param o object that is being compared
     * @return If the object and this is equal
     */

    @Override
    public boolean equals(Object o){
        User u = (User)o;
        if (o == null){
            return false;
        }
        if (getClass() != o.getClass()){
            return false;
        }
        if (o == this){
            return true;
        }
        //Is O null?
        //Is o == this?
        //do this and o have the same class?

        //do this and o have the same wordCount and nodeCount?
        if (!Objects.equals(u.personID, this.personID)){
            return false;
        }
        if (!Objects.equals(this.getUsername(), u.getUsername())){
            return false;
        }

        if (!Objects.equals(u.getFirstName(), this.getFirstName())){
            return false;
        }
        if (!Objects.equals(u.getLastName(), this.getLastName())){
            return false;
        }
        if (!Objects.equals(u.getGender(), this.getGender())){
            return false;
        }

        if (!Objects.equals(u.getEmail(), this.getEmail())){
            return false;
        }
        if (!Objects.equals(u.getPersonID(), this.personID)){
            return false;
        }

        return true;
    }
}
