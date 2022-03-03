package Service;

import DataAccess.*;
import Model.Authorization;
import Model.Person;
import Model.User;
import Request.userRegisterRequest;
import Response.userRegisterResponse;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

/**
 * Gets the Reqest and Repsonses for User/Register Call
 */
public class userRegister {

    private userRegisterResponse response;
    private userRegisterRequest request;
    private String auth;
    private String username;
    private String personID;

    public userRegister(userRegisterRequest userReq) throws DataAccessException {
        this.request = userReq;
        boolean success = false;
        Database data = new Database();
        try {
        //Check to see if the username already exists
        String randomID = null;
        UserDao userD = new UserDao(data.openConnection());
        if (userD.searchUsername(userReq.getUsername())) {
            throw new DataAccessException("There is already a user with that name, userRegister");
        }
        else {
            //Add new User with personID
            boolean newID = false;
            PersonDao pDAO = new PersonDao(data.getConnection());
            while(!newID) {
                randomID = UUID.randomUUID().toString();
                personID = randomID;
                newID = pDAO.searchPersonID(randomID);
            }

            User newUser = new User(userReq.getUsername(), userReq.getPassword(), userReq.getEmail(), userReq.getFirstName(), userReq.getLastName(), userReq.getGender()
            , randomID);

             auth = userD.insertUser(newUser);
             username = newUser.getUsername();

             //Log the user in
            Authorization auther = new Authorization(newUser.getUsername(), auth);
            AuthorizationDao authorizationDao = new AuthorizationDao(data.getConnection());
            authorizationDao.insertUser(auther);
             //Generate the family needed for each user
            GenerateData generate = new GenerateData(newUser, 5, data.getConnection());
            List<Person> personList = generate.getListOfPeople();
            System.out.println("Made people");
            for (int i = 0; i  <personList.size(); i++){
                pDAO.insertPerson(personList.get(i));
            }


          }
        success = true;


        } catch (DataAccessException ex){
            data.closeConnection(false);
            throw new DataAccessException("There is already a user with that name, userRegister");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (success){
            response = new userRegisterResponse(username, auth, personID, success);

        } else {
            response = new userRegisterResponse("Could NOT register the user!", false);
        }

        data.closeConnection(true);


    }

    public userRegisterResponse getResponse(){
        return response;
    }


    /**
     * Create a new user and add to database
     */
    public void newUser(){

    }

    /**
     * Creates the family tree for the user
     * @param username the username for the family
     * @param generations how many generations to create
     */
    public void generateFamily(String username, int generations){


    }

    /**
     * Logs the user in as set as the active user
     */
    //TODO: HOW TO LOG THE USER IN
    public void logUserIn(){

    }

    /**
     * returns the auth token of the user
     * @param username the user that needs to be associated with the auth token
     * @return the auth token
     */
    public String getAuthToken(String username){

        return null;

    }

}
