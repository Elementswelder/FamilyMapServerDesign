package Service;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import Response.PersonIDResponse;

import java.sql.Connection;

/**
 * Creates a class of the PersonID
 */
public class PersonID {
    String authToken;
    String personID;

    public PersonID(String authToken, String personID){
        this.authToken = authToken;
        this.personID = personID;

    }

    /**
     * Get a person and the info and find based on the authtoken
     * @return return the Person Found
     */
    public PersonIDResponse findUser() throws DataAccessException {
        Database data = new Database();
        Connection connection = data.openConnection();
        //Check the Auth match
        String username;
        AuthorizationDao authorizationDao = new AuthorizationDao(connection);
        try {
           username = authorizationDao.findUser(authToken);
        } catch (DataAccessException ex){
            data.closeConnection(false);
            return new PersonIDResponse("Error: Internal server error, could not match", false);
        }
        if (username == null){
            data.closeConnection(false);
            return new PersonIDResponse("Error: No auth token matching", false);
        }

        //Check the person match
        PersonDao personDao = new PersonDao(connection);
        Person person;
        try {
            person = personDao.findPerson(personID);
        }catch (DataAccessException ex){
            return new PersonIDResponse("Error: Failed to find a person", false);
        }
        if (!person.getUsername().equals(username)){
            data.closeConnection(false);
            return new PersonIDResponse("Error: Person and associated username do not match", false);
        }

        data.closeConnection(true);
        return new PersonIDResponse(person.getUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender()
                ,checkNullFather(person), checkNullMother(person), checkNullSpouse(person), true);
    }

    private String checkNullFather(Person person){
        if (person.getFatherID() == null) {
            return "null";
        }
        else {
            return person.getFatherID();
        }
    }

    private String checkNullMother(Person person){
        if (person.getMotherID() == null) {
            return "null";
        }
        else {
            return person.getMotherID();
        }
    }

    private String checkNullSpouse(Person person){
        if (person.getSpouseID() == null) {
            return "null";
        }
        else {
            return person.getSpouseID();
        }
    }
}
