package passoff;

import DataAccess.*;
import Model.Authorization;
import Model.Person;
import Response.PersonIDResponse;
import Service.PersonID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonIDTest {
    private Database db;
    private PersonIDResponse personResponse;
    private PersonID personsID;
    private AuthorizationDao authDao;
    private Authorization auth;
    private Person person;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        person = new Person("Alpha", "user", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        personDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void findUserPass() throws DataAccessException {
        auth = new Authorization("user", "bob");
        authDao = new AuthorizationDao(db.getConnection());
        authDao.insertUser(auth);
        personDao.insertPerson(person);
        db.closeConnection(true);
        personsID = new PersonID("bob", "Alpha");
        personResponse = personsID.findUser();
        //So lets use a find method to g et the event that we just put in back out
        assertEquals(person.getPersonID(), personResponse.getPersonID());
        db.openConnection();

    }

    @Test
    public void findUserFail() throws DataAccessException {
        personsID = new PersonID("user", "test");
        personResponse = personsID.findUser();
        //So lets use a find method to get the event that we just put in back out
        assertTrue(personResponse.getMessage().contains("Error"));
    }

}
