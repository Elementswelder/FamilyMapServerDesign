package Service;

import DataAccess.*;
import Model.Person;
import Response.PersonRRResponse;

import java.sql.Connection;
import java.util.*;

/**
 * Class of the /person call
 */
public class PersonRR {
    private Set<Person> personData = new HashSet<>();
    String authToken;


    public PersonRR(String authToken){
        this.authToken = authToken;

    }

    /**
     * Return all the family members with the associated user
     * @return the list of family members
     */
    public PersonRRResponse getFamily() throws DataAccessException {
        Database data = new Database();
        Connection connection = data.openConnection();
        //Check the Auth match
        AuthorizationDao authorizationDao = new AuthorizationDao(connection);
        String username = authorizationDao.findUser(authToken);
        if (username == null){
            data.closeConnection(false);
            return new PersonRRResponse("Error: No authtoken matching with anyone!", false);
        }
        //Check the person match
        PersonDao personDao = new PersonDao(connection);
        personData = personDao.listOfPersons(username);
        if (personData.size() == 0){
            data.closeConnection(false);
            return new PersonRRResponse("Error:No people associated with this person", false);
        }
        data.closeConnection(true);
        return new PersonRRResponse(
                personData, true);
    }

}
