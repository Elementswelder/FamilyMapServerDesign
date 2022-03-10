package passoff;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.Authorization;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationDAOTest {

    private Database db;
    private Authorization auth;
    private AuthorizationDao authorizationDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
         auth = new Authorization("Bobby", "123456");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        authorizationDao = new AuthorizationDao(conn);
    }

    @AfterEach
    public void closeConnection() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void testInsert() throws DataAccessException {
        authorizationDao.insertUser(auth);

        Authorization compareAuth = authorizationDao.findAuth(auth.getUsername());
        //Make sure that they found something
        assertNotNull(compareAuth);

        assertEquals(compareAuth, auth);
    }


    @Test
    public void testInsertFail() throws DataAccessException {
        authorizationDao.insertUser(auth);
        assertThrows(DataAccessException.class, ()-> authorizationDao.insertUser(auth));
    }



    @Test
    public void testFind() throws DataAccessException {
        authorizationDao.insertUser(auth);
        Authorization newAuth = new Authorization("Billy", "654321");

        authorizationDao.insertUser(newAuth);
        Authorization compareAuth = authorizationDao.findAuth(auth.getUsername());
        //Make sure that they found something
        assertNotNull(compareAuth);

        assertEquals(compareAuth, auth);

        compareAuth = authorizationDao.findAuth(newAuth.getUsername());

        assertNotNull(compareAuth);
        assertEquals(compareAuth, newAuth);
    }


    @Test
    public void testFindFail() throws DataAccessException {
        authorizationDao.insertUser(auth);
        Authorization newAuth = new Authorization("Billy", "654321");
        authorizationDao.insertUser(newAuth);

        assertThrows(DataAccessException.class, ()-> authorizationDao.findAuth("Test"));

    }

    @Test
    public void testFindUsername() throws DataAccessException {
        authorizationDao.insertUser(auth);
        Authorization newAuth = new Authorization("Billy", "654321");

        authorizationDao.insertUser(newAuth);
        String compareAuth = authorizationDao.findUser(auth.getAuthToken());
        //Make sure that they found something
        assertNotNull(compareAuth);

        assertEquals(compareAuth, auth.getUsername());

    }


    @Test
    public void testFindUsernameFail() throws DataAccessException {
        assertNull(authorizationDao.findUser("Test"));

    }


}
