package passoff;

import DataAccess.*;
import Model.Authorization;
import Model.Event;
import Model.User;
import Service.EventID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Response.EventIDResponse;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventIDTest {
    private Database db;
    private Event bestEvent;
    private EventDao eDao;
    private EventIDResponse eventResponse;
    private EventID eventID;
    private AuthorizationDao authDao;
    private Authorization auth;
    private User user;
    private UserDao userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEvent = new Event("Biking_123", "user", "Gale123",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        eDao = new EventDao(conn);
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
        eDao.insert(bestEvent);
        auth = new Authorization("user", "bob");
        authDao = new AuthorizationDao(db.getConnection());
        authDao.insertUser(auth);
        eventID = new EventID("bob", bestEvent.getEventID());
        db.closeConnection(true);
        eventResponse = eventID.findUser();
        //So lets use a find method to g et the event that we just put in back out
        assertEquals(bestEvent.getEventID(), eventResponse.getEventID());
        db.openConnection();

    }

    @Test
    public void findUserFail() throws DataAccessException {
        eDao.insert(bestEvent);
        eventID = new EventID("123","test");
        eventResponse = eventID.findUser();
        //So lets use a find method to get the event that we just put in back out
        assertTrue(eventResponse.getMessage().contains("Error"));
    }

}
