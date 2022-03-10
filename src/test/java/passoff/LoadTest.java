package passoff;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.RegisterRequest;
import Response.LoadResponse;
import Service.Load;
import Service.UserRegister;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//We will use this to test that our insert method is working and failing in the right ways
public class LoadTest {
    private Database db;
    private Load load;
    private LoadRequest loadRequest;
    private LoadResponse loadResponse;
    private Connection conn;



    @BeforeEach
    public void setUp() throws DataAccessException, IOException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
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
    public void loadPass() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        load = new Load(loadRequest);
        assertEquals(2, loadRequest.getUsers().length);
        assertEquals(11, loadRequest.getPersons().length);
        assertEquals(19, loadRequest.getEvents().length);
        loadResponse = load.startLoading();

        assertTrue(loadResponse.getMessage().contains("19"));
        db.openConnection();

    }

    @Test
    //Testing to see what happens if it's loaded in twice
    public void loadPassTwo() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        load = new Load(loadRequest);
        assertEquals(2, loadRequest.getUsers().length);
        assertEquals(11, loadRequest.getPersons().length);
        assertEquals(19, loadRequest.getEvents().length);
        loadResponse = load.startLoading();

        assertTrue(loadResponse.getMessage().contains("19"));
        gson = new Gson();
        jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        load = new Load(loadRequest);
        assertEquals(2, loadRequest.getUsers().length);
        assertEquals(11, loadRequest.getPersons().length);
        assertEquals(19, loadRequest.getEvents().length);
        loadResponse = load.startLoading();

        assertTrue(loadResponse.getMessage().contains("19"));
        db.openConnection();

    }

    @Test
    public void clearPass() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        load = new Load(loadRequest);
        assertTrue(load.clear());
        db.openConnection();
    }

    @Test
    public void clearPassTwo() throws DataAccessException, FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        //We are creating a LoadRequest from the JsonReader we made
        loadRequest = gson.fromJson(jsonReader, LoadRequest.class);
        load = new Load(loadRequest);
        Person personThree = new Person("Alpha3", "Superperson2", "The",
                "Best", "m", "Superman", "Superwoman",
                "Perfect");
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.insertPerson(personThree);
        db.closeConnection(true);
        assertTrue(load.clear());
        db.openConnection();


    }

}



