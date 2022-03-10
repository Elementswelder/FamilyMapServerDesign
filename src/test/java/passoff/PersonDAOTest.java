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
        //Then we pass that connection to the PersonDAO so it can access the database
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

        Set<Person> combine = personDAO.listOfPersons(personTwo.getUsername());
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

    @Test
    public void testClearTwo() throws DataAccessException {
        personDAO.clearPersonTable();

        Set<Person> combine = personDAO.getAllPeople();
        assertEquals(0, combine.size());


    }

    @Test
    public void listPersonsPass() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personTwo);
        personDAO.insertPerson(personThree);
        Set<Person> listPerson = personDAO.listOfPersons("Superperson2");
        assertEquals(2, listPerson.size());
    }

    @Test
    public void listPersonsFail() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        Set<Person> listPerson = personDAO.listOfPersons("Test");
        assertEquals(0, listPerson.size());
    }

    @Test
    public void associatedUsernamePass() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        Set<Person> list = personDAO.getAllPeople();
        assertEquals(3, list.size());

        personDAO.clearAssociatedUsername("Superperson2");
        list = personDAO.getAllPeople();
        assertEquals(1, list.size());

    }

    @Test
    public void associatedUsernamePassTwo() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        Set<Person> list = personDAO.getAllPeople();
        assertEquals(3, list.size());

        personDAO.clearAssociatedUsername("Superperson2");
        list = personDAO.getAllPeople();
        assertEquals(1, list.size());

        personDAO.clearAssociatedUsername("Superperson");
        list = personDAO.getAllPeople();
        assertEquals(0, list.size());

    }

    @Test
    public void getAllPeoplePass() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        Set<Person> list = personDAO.getAllPeople();
        assertEquals(3, list.size());

    }

    @Test
    public void getAllPeoplePassTwo() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        Set<Person> list = personDAO.getAllPeople();
        assertEquals(3, list.size());

        personDAO.clearAssociatedUsername("Superperson");
        list = personDAO.getAllPeople();
        assertEquals(2, list.size());

        personDAO.clearAssociatedUsername("Superperson2");
        list = personDAO.getAllPeople();
        assertEquals(0, list.size());

        personDAO.insertPerson(person);
        list = personDAO.getAllPeople();
        assertEquals(1, list.size());
    }

    @Test
    public void searchPersonPass() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        boolean search = personDAO.searchPersonID("Alpha2");
        assertTrue(search);


    }

    @Test
    public void searchPersonFail() throws DataAccessException{
        personDAO.insertPerson(person);
        Person personTwo = new Person("Alpha2", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        personDAO.insertPerson(personThree);
        personDAO.insertPerson(personTwo);
        boolean search = personDAO.searchPersonID("Alpha5");
        assertFalse(search);

    }



}
