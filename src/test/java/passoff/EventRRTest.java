package passoff;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Authorization;
import Model.Event;
import Response.EventRRResponse;
import Service.EventRR;
import Service.PersonRR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//We will use this to test that our insert method is working and failing in the right ways
public class EventRRTest {
    private Database db;
    private EventRRResponse eventResponse;
    private EventRR eventRR;
    private AuthorizationDao authDao;
    private Authorization auth;
    private Event event, eventTwo, eventThree;
    private EventDao eventDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        event = new Event("Biking_124", "user", "Gale123",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventTwo = new Event("Biking_125", "user", "Gale123",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventThree = new Event("Biking_126", "user", "Gale123",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        eventDao = new EventDao(conn);
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
        eventDao.insert(event);
        eventDao.insert(eventTwo);
        eventDao.insert(eventThree);
        db.closeConnection(true);
        eventRR = new EventRR("bob");
        eventResponse = eventRR.getEvents();
        //So lets use a find method to g et the event that we just put in back out
        assertEquals(3, eventResponse.getData().size());
        db.openConnection();

    }

    @Test
    public void findUserFail() throws DataAccessException {
        eventRR = new EventRR("user");
        eventResponse = eventRR.getEvents();
        //So lets use a find method to get the event that we just put in back out
        assertTrue(eventResponse.getMessage().contains("Error"));
    }

}
