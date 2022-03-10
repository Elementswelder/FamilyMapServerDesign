package passoff;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.UserLoginResponse;
import Response.UserRegisterResponse;
import Service.Clear;
import Service.UserLogin;
import Service.UserRegister;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class UserRegisterTest {
    private Database db;
    private UserRegister userRegister;
    private RegisterRequest registerRequest;
    private UserRegisterResponse userRegisterResponse;
    private Connection conn;



    @BeforeEach
    public void setUp() throws DataAccessException, IOException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        setupData();
        //Then we pass that connection to the EventDAO so it can access the database
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.clearTables();
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(true);
    }

    @Test
    //Testing to clear the entire database
    public void registerUserPass() throws DataAccessException {
        registerRequest = new RegisterRequest("user", "password", "user@gmail.com", "us", "er", "male");
        userRegister = new UserRegister(registerRequest);

        userRegisterResponse = userRegister.registerUser();
        conn = db.openConnection();
        UserDao userDao = new UserDao(conn);
        User user = userDao.findUser(registerRequest.getUsername());
        assertEquals(user.getUsername(), registerRequest.getUsername());
        assertEquals(user.getPassword(), registerRequest.getPassword());


    }

    @Test
    //Testing to make sure that clear works
    public void registerUserFail() throws DataAccessException {
        registerRequest = new RegisterRequest("user", "password", "user@gmail.com", "us", "er", "male");
        userRegister = new UserRegister(registerRequest);
        userRegisterResponse = userRegister.registerUser();
        registerRequest = new RegisterRequest("user", "password", "user@gmail.com", "us", "er", "male");
        userRegister = new UserRegister(registerRequest);
        userRegisterResponse = userRegister.registerUser();
        assertTrue(userRegisterResponse.getMessage().contains("Error"));
        db.openConnection();

    }


    private void setupData() throws IOException {
        Gson gson = new Gson();

        //Load the locations into Location Class
        File fileLocation = new File("json/locations.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            LocationList.LocationListStatic locations = gson.fromJson(fileReader, LocationList.LocationListStatic.class);
            //NEEDED TO MAKE THE DATA STATIC, DO NOT REMOVE
            locations.setStatic();
            System.out.println("Loaded Locations");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLocatoin");
            ex.printStackTrace();
        }

        //Load the fNames into Firstname List
        fileLocation = new File("json/fnames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            FirstNameList.firstNameStatic fList = new FirstNameList.firstNameStatic(nameArr);
            System.out.println("Loaded First Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLocatoin");
            ex.printStackTrace();
        }

        //Load the last names
        fileLocation = new File("json/snames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            LastNameList.lastNameStatic lList = new LastNameList.lastNameStatic(nameArr);
            System.out.println("Loaded Last Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLastName");
            ex.printStackTrace();
        }

        //Load the middle names
        fileLocation = new File("json/mnames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            MiddleNameList.middleNameStatic mList = new MiddleNameList.middleNameStatic(nameArr);
            System.out.println("Loaded Middle Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLastName");
            ex.printStackTrace();
        }

    }

}



