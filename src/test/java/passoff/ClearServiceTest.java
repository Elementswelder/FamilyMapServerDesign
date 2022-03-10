package passoff;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Service.Clear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class ClearServiceTest {
    private Database db;
    private Clear clearIt;
    private Person person;
    private PersonDao personDao;
    private Event event;
    private EventDao eventDao;
    private User user;
    private UserDao userDao;
    private Connection conn;



    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        conn = db.getConnection();
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    //Testing to clear the entire database
    public void clearPass() throws DataAccessException {

        Event newTwo = new Event("Biking_125", "Gale", "Gale12",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDao = new PersonDao(conn);
        personDao.insertPerson(personThree);
        userDao = new UserDao(conn);
        userDao.insertUser(userThree);
        eventDao = new EventDao(conn);
        eventDao.insert(newTwo);

        assertEquals(1, personDao.getAllPeople().size());
        assertEquals(1, eventDao.listEvents("Gale").size());
        assertEquals(1, userDao.allUsers().size());
        db.closeConnection(true);
        clearIt = new Clear();
        assertTrue(clearIt.clear());
        conn = db.openConnection();
        personDao = new PersonDao(conn);
        userDao = new UserDao(conn);
        eventDao = new EventDao(conn);

        assertEquals(0, personDao.getAllPeople().size());
        assertEquals(0, eventDao.listEvents("Gale").size());
        assertEquals(0, userDao.allUsers().size());
    }

    @Test
    //Testing to make sure that clear works
    public void clearPassTwo() throws DataAccessException {
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDao = new PersonDao(conn);
        personDao.insertPerson(personThree);
        assertEquals(1, personDao.getAllPeople().size());
        db.closeConnection(true);
        clearIt = new Clear();
        assertTrue(clearIt.clear());
        personDao = new PersonDao(db.openConnection());
        assertEquals(0, personDao.getAllPeople().size());

    }

}
