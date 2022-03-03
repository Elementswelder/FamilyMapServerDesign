package passoff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person person;
    private PersonDao personDAO;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        person = new Person("Alpha1", "Superperson", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        personDAO = new PersonDao(conn);
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
        personDAO.insertPerson(person);

        Person comparePeople = personDAO.findPerson(person.getPersonID());
        //Make sure that they found something
        assertNotNull(comparePeople);

        assertEquals(person, comparePeople);
    }


    @Test
    public void testInsertFail() throws DataAccessException {
        personDAO.insertPerson(person);
        assertThrows(DataAccessException.class, ()-> personDAO.insertPerson(person));
    }

    @Test
    public void testFind() throws DataAccessException {
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(person);
        personDAO.insertPerson(personTwo);
        personDAO.insertPerson(personThree);

        Set<Person> combine = personDAO.listofPersons(personTwo.getUsername());
        assertNotNull(combine);
        //
        assertEquals(2, combine.size());
    }

    @Test
    public void testFindFail() throws DataAccessException {
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(person);
        personDAO.insertPerson(personTwo);
        personDAO.insertPerson(personThree);

        assertThrows(DataAccessException.class, ()-> personDAO.findPerson("Test"));

    }

    @Test
    public void testClear() throws DataAccessException {
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(person);
        personDAO.insertPerson(personTwo);
        personDAO.insertPerson(personThree);


        personDAO.clearPersonTable();

        Set<Person> combine = personDAO.getAllPeople();
        assertEquals(0, combine.size());


    }


}
