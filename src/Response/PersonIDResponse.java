package Response;

/**
 * Creates a class of the PersonID
 */
public class PersonIDResponse {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private String message;
    private boolean success;


    /**
     * A constructor to create the response body for the personID
     * @param associatedUser the username
     * @param personID the personID
     * @param fName the first name
     * @param lName the last name
     * @param gender the gender
     * @param father the father (optional check for null)
     * @param mother the mother (optional check for null)
     * @param spouse the spouse (optional check for null)
     * @param success if it was successful or not
     */

    public PersonIDResponse(String associatedUser, String personID, String fName, String lName,
                            String gender, String father, String mother, String spouse, boolean success){
        this.associatedUsername = associatedUser;
        this.personID = personID;
        this.firstName = fName;
        this.lastName = lName;
        this.gender = gender;
        this.fatherID = father;
        this.motherID = mother;
        this.spouseID = spouse;
        this.success = success;
    }
    public PersonIDResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUser() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getfName() {
        return firstName;
    }

    public String getlName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return fatherID;
    }

    public String getMother() {
        return motherID;
    }

    public String getSpouse() {
        return spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage(){
        return message; }

    public void setDataNull(){
        this.associatedUsername = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender =  null;
        this.fatherID =  null;
        this.motherID =  null;
        this.spouseID =  null;

    }

    public void setMessageNull(){
        this.message = null;
    }
}
