package Service;

import Model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the /person call
 */
public class personRR {
    private List<Person> personData = new ArrayList<>();
    boolean validate;

    /**
     * Return all the family members with the assocaited user
     * @param authToken how to find the current user
     * @return the list of family members
     */
    public List<Person> getFamily(String authToken){

        return null;
    }
}
