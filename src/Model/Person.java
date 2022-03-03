package Model;
//Person Getters and Setters

import java.util.Objects;

/**
 * Person Class that will have getters and setters based on the Person Object
 */
public class Person {
    private String personID;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * The constructor of a person to an object
     * @param personID the person ID for the object
     * @param username the username associated with the Person
     * @param firstName the First Name of the person
     * @param lastName the Last Name of the person
     * @param gender the gender of the person
     * @param fatherID the Father (if any) of the person
     * @param motherID the mother (if any) of the person
     * @param spouseID the spouse (if any) of the person
     */
    public Person(String personID, String username, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID){
        this.personID = personID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }


    /**
     * Override the equals method
     * @param o The object being compared
     * @return Return true or false based on if the objects are equals or not
     */
    @Override
    public boolean equals(Object o){
        Person p = (Person)o;
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
        if (!Objects.equals(p.personID, this.personID)){
            return false;
        }
        if (!Objects.equals(this.getUsername(), p.getUsername())){
            return false;
        }

        if (!Objects.equals(p.getFirstName(), this.getFirstName())){
            return false;
        }
        if (!Objects.equals(p.getLastName(), this.getLastName())){
            return false;
        }
        if (!Objects.equals(p.getGender(), this.getGender())){
            return false;
        }

        if (!Objects.equals(p.getFatherID(), this.getFatherID())){
            return false;
        }
        if (!Objects.equals(p.getMotherID(), this.getMotherID())){
            return false;
        }
        if (!Objects.equals(p.getSpouseID(), this.getSpouseID())){
            return false;
        }

        return true;
    }
}
