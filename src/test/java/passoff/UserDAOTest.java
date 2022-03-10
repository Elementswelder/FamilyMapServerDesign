package passoff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User user;
    private UserDao userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        user = new User("Beta1", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta1");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        userDAO = new UserDao(conn);
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
        userDAO.insertUser(user);

        User compareUsers = userDAO.findUser(user.getUsername());
        //Make sure that they found something
        assertNotNull(compareUsers);

        assertEquals(user, compareUsers);
    }


    @Test
    public void testInsertFail() throws DataAccessException {
        userDAO.insertUser(user);
        assertThrows(DataAccessException.class, ()-> userDAO.insertUser(user));
    }

    @Test
    public void testFind() throws DataAccessException {
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        User userThree = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta");
        userDAO.insertUser(user);
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);

        Set<User> combine = userDAO.listOfUsers(userThree.getUsername());
        assertNotNull(combine);
        //
        assertEquals(2, combine.size());
    }

    @Test
    public void testFindFail() throws DataAccessException {
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(user);
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);

        assertThrows(DataAccessException.class, ()-> userDAO.findUser("Test"));

    }

    @Test
    public void testClear() throws DataAccessException {
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(user);
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);


        userDAO.clearUser();

        Set<User> combine = userDAO.allUsers();
        assertEquals(0, combine.size());
    }

    @Test
    public void testClearTwo() throws DataAccessException {
        userDAO.clearUser();
        Set<User> combine = userDAO.allUsers();
        assertEquals(0, combine.size());
    }

    @Test
    public void getAllPeoplePass() throws DataAccessException{
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        Set<User> list = userDAO.allUsers();
        assertEquals(3, list.size());
    }

    @Test
    public void getAllPeoplePassTwo() throws DataAccessException{
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        Set<User> list = userDAO.allUsers();
        assertEquals(3, list.size());

        userDAO.clearUser();
        list = userDAO.allUsers();
        assertEquals(0, list.size());

        userDAO.insertUser(userTwo);
        list = userDAO.allUsers();
        assertEquals(1, list.size());

        userDAO.insertUser(user);
        list = userDAO.allUsers();
        assertEquals(2, list.size());
    }

    @Test
    public void listOfUsersPass() throws DataAccessException {
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        Set<User> list = userDAO.listOfUsers("Beta2");
        assertEquals(1, list.size());
    }

    @Test
    public void listofUsersFail() throws DataAccessException{
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        Set<User> list = userDAO.listOfUsers("test");
        assertEquals(0, list.size());

    }

    @Test
    public void searchUsernamePass() throws DataAccessException {
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        assertTrue(userDAO.searchUsername("Beta2"));
    }
    @Test
    public void searchUsernameFail() throws DataAccessException {
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        assertFalse(userDAO.searchUsername("Nothing"));
    }

    @Test
    public void searchAccountPass() throws DataAccessException{
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        assertTrue(userDAO.searchAccount("Beta2", "alphabeta"));
        assertTrue(userDAO.searchAccount("Beta3", "alphabeta"));
    }
    @Test
    public void searchAccountFail() throws DataAccessException{
        userDAO.insertUser(user);
        User userTwo = new User("Beta2", "alphabeta", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta2");
        User userThree = new User("Beta3", "alphabet5", "ab@gmail.com",
                "The", "Dude", "m", "alphabeta3");
        userDAO.insertUser(userTwo);
        userDAO.insertUser(userThree);
        assertFalse(userDAO.searchAccount("Beta", "alphabeta"));
        assertFalse(userDAO.searchAccount("Beta3", "alphabe"));
        assertFalse(userDAO.searchAccount("b", "alpha"));
    }


}
