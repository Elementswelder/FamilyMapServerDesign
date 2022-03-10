package Service;

import DataAccess.*;
import Model.Authorization;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Response.UserRegisterResponse;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * Gets the Request and Response for User/Register Call
 */
public class UserRegister {

    private RegisterRequest request;
    private String username;
    Database data;

    public UserRegister(RegisterRequest request) throws DataAccessException {
        this.request = request;
        data = new Database();

    }
    //Start the service methods for Register
    public UserRegisterResponse registerUser() throws DataAccessException {

        String auth = null;
        String newPersonID = null;
        boolean success = false;
        try {
            //Check to see if the username already exists
            UserDao userD = new UserDao(data.openConnection());
            if (userD.searchUsername(request.getUsername())) {
                data.closeConnection(false);
                return new UserRegisterResponse("Error: There is already a user with that username", false);

            }
            else {
                if (checkValid()) {
                    //Add new User with personID
                    PersonDao pDAO = new PersonDao(data.getConnection());
                    newPersonID = UUID.randomUUID().toString();

                    User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender()
                            , newPersonID);

                    auth = userD.insertUser(newUser);
                    username = request.getUsername();

                    //Log the user in
                    Authorization newAuth = new Authorization(newUser.getUsername(), auth);
                    AuthorizationDao authorizationDao = new AuthorizationDao(data.getConnection());
                    authorizationDao.insertUser(newAuth);
                    //Generate the family needed for each user
                    GenerateData generate = new GenerateData(newUser, 5, data.getConnection());
                    List<Person> personList = generate.getListOfPeople();
                    System.out.println("Made people");
                    for (Person person : personList) {
                        pDAO.insertPerson(person);
                    }
                }
                else {
                    data.closeConnection(false);
                    return new UserRegisterResponse("Error:There is a missing value in the user", false);
                }
                success = true;
            }

        } catch (DataAccessException ex){
            data.closeConnection(false);
            throw new DataAccessException("There is already a user with that name, userRegister");
        } catch (FileNotFoundException e) {
            data.closeConnection(false);
            e.printStackTrace();
        }

        if (success){
            //Return a successful message
            data.closeConnection(true);
            return new UserRegisterResponse(request.getUsername(), auth, newPersonID, true);
        } else {
            data.closeConnection(false);
            return new UserRegisterResponse("Error: There is an internal server error", false);
        }
    }

    public boolean checkValid(){

        if (request.getUsername() == null){
            return false;
        }
        if (request.getPassword() == null){
            return false;
        }
        if (request.getFirstName() == null){
            return false;
        }
        if (request.getLastName() == null){
            return false;
        }
        return request.getGender() != null;

    }
}
