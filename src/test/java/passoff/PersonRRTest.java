package passoff;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Authorization;
import Model.Person;
import Response.PersonRRResponse;
import Service.PersonRR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonRRTest {
    private Database db;
    private PersonRRResponse personResponse;
    private PersonRR personRR;
    private AuthorizationDao authDao;
    private Authorization auth;
    private Person person, personTwo, personThree;
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
        personTwo = new Person("Alpha1", "user", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personThree = new Person("Alpha2", "user", "The",
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
        personDao.insertPerson(personTwo);
        personDao.insertPerson(personThree);
        db.closeConnection(true);
        personRR = new PersonRR("bob");
        personResponse = personRR.getFamily();
        //So lets use a find method to g et the event that we just put in back out
        assertEquals(3, personResponse.getPersonData().size());
        db.openConnection();

    }

    @Test
    public void findUserFail() throws DataAccessException {
        personRR = new PersonRR("user");
        personResponse = personRR.getFamily();
        //So lets use a find method to get the event that we just put in back out
        assertTrue(personResponse.getMessage().contains("Error"));
    }

}
