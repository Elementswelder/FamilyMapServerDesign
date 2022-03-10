package Service;

import DataAccess.AuthorizationDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import Response.EventIDResponse;

import java.sql.Connection;

/**
 * Event/EventID class API call
 */
public class EventID {

    String authToken;
    String eventID;

    public EventID(String authToken, String eventID){
        this.authToken = authToken;
        this.eventID = eventID;

    }

    /**
     * Get a event and the info and find based on the authtoken
     * @return return the Person Found
     */
    public EventIDResponse findUser() throws DataAccessException {
        Database data = new Database();
        Connection connection = data.openConnection();
        //Check the Auth match
        AuthorizationDao authorizationDao = new AuthorizationDao(connection);
        String username = authorizationDao.findUser(authToken);
        if (username == null){
            data.closeConnection(false);
            return new EventIDResponse("Error: No authtoken matching with anyone", false);
        }
        //Check the event match
        EventDao eventDao = new EventDao(connection);
        Event event = eventDao.find(eventID);
        try {
            if (event.getClass() == null || !event.getAssociatedUsername().equals(username)) {
                data.closeConnection(false);
                return new EventIDResponse("Error: Event and username do not match", false);

            }
        }catch (NullPointerException ex){
            data.closeConnection(false);
            return new EventIDResponse("Event not found, null pointer", false);

        }
        data.closeConnection(true);
        return new EventIDResponse(event.getEventID(),event.getAssociatedUsername(), event.getPersonID(),
                event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), true);
    }

}

