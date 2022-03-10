package Service;

import DataAccess.*;
import Model.Authorization;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.LoadResponse;
import java.sql.Connection;

/**
 * Clears then loads all the information into the database
 */
public class Load {

    private LoadResponse response;
    private LoadRequest request;
    private User[] userList;
    private Person[] personList;
    private Event[] eventList;
    private int eventListNum;
    private int personListNum;
    private int userListNum;

    public Load(LoadRequest request) throws DataAccessException {
        this.request = request;
        eventList = request.getEvents();
        personList = request.getPersons();
        userList = request.getUsers();
    }

    /**
     * Loads the data from request into the database
     */
    public LoadResponse startLoading() throws DataAccessException {

        boolean clearSuccess = clear();
        if (!clearSuccess){
            return new LoadResponse("Error:Unable to clear in Load", false);
        }
        Database data = new Database();
        Connection connect = data.openConnection();
        //Clear the Database before loading
        boolean checkClear = clear();
        if (checkClear) {
            //Load User
            UserDao userDao = new UserDao(connect);

            for (User user : userList) {
               String auth = userDao.insertUser(user);
                String username = user.getUsername();
                //Log the user in
                Authorization newAuth = new Authorization(user.getUsername(), auth);
                AuthorizationDao authorizationDao = new AuthorizationDao(data.getConnection());
                authorizationDao.insertUser(newAuth);
            }
            //Load Person
            PersonDao personDao = new PersonDao(connect);
            for (Person person : personList) {
                personDao.insertPerson(person);
            }
            //Load Event
            EventDao eventDao = new EventDao(connect);
            for (Event event : eventList) {
                eventDao.insert(event);
            }

            data.closeConnection(true);
            response = new LoadResponse("Successfully added " + userList.length + " users, " +
                    personList.length + " persons, and " + eventList.length + " events to the database",true);
        }
        else {
            data.closeConnection(false);
            response = new LoadResponse("Error:Failed to clear database in load", false);


        }
        return response;
    }

    /**
     * Clear all the data access objects!
     */
    public boolean clear(){
        Clear newClear = new Clear();
       boolean cleared =  newClear.clear();
       return cleared;
    }

    private boolean validDataUser(User user){
        if (user.getUsername() == null){
            return false;
        }
        if (user.getPassword() == null){
            return false;
        }
        if (user.getEmail() == null){
            return false;
        }
        if (user.getFirstName() == null){
            return false;
        }
        if (user.getLastName() == null){
            return false;
        }
        if (user.getGender() == null){
            return false;
        }
        if (user.getPersonID() == null){
            return false;
        }
        return true;
    }

    private boolean validDataEvent(Event event){
        if (event.getEventID() == null){
            return false;
        }
        if (event.getAssociatedUsername() == null){
            return false;
        }
        if (event.getPersonID() == null){
            return false;
        }
        if (event.getLatitude() == 0){
            return false;
        }
        if (event.getLongitude() == 0){
            return false;
        }
        if (event.getCountry() == null){
            return false;
        }
        if (event.getCity() == null){
            return false;
        }
        if (event.getEventType() == null){
            return false;
        }
        if (event.getYear() == 0){
            return false;
        }
        return true;
    }

    public LoadResponse getResponse(){

        return response;
    }


}
