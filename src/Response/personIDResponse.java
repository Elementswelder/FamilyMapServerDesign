package Response;

/**
 * Creates a class of the PersonID
 */
public class personIDResponse {
    private String associatedUser, personID, fName, lName, gender, father, mother, spouse;
    private boolean validate;


    /**
     * A constructor to create the reponse body for the personID
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

    public personIDResponse(String associatedUser, String personID, String fName, String lName,
                            String gender, String father, String mother, String spouse, boolean success){
        this.associatedUser = associatedUser;
        this.personID = personID;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
        this.validate = success;
    }

    public String getAssociatedUser() {
        return associatedUser;
    }

    public String getPersonID() {
        return personID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public boolean isValidate() {
        return validate;
    }
}
