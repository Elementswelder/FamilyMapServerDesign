package Response;

import Model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the /person response
 */
public class personResponse {
    private List<Person> personData = new ArrayList<>();
    boolean validate;
    private String message;

    /**
     * Constructor for the response body for person
     * @param listofPeople the list of people from the JSON file
     * @param success if it was successfull
     */
    public personResponse(List<Person> listofPeople, boolean success){
            this.personData = listofPeople;
            this.validate = success;
        }

    /**
     * Error construtor when it fails
     * @param message the message associated with the error
     * @param success failure
     */
    public personResponse(String message, boolean success){
        this.message = message;
        this.validate = success;
    }

    public List<Person> getPersonData() {
        return personData;
    }

    public boolean isValidate() {
        return validate;
    }

    public String getMessage() {
        return message;
    }
}
