package Service;

import DataAccess.*;
import Model.Event;
import Response.EventRRResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * Class based on the /event API call and retrieves a list of events
 */
public class EventRR {
    private Set<Event> eventData = new HashSet<>();
    String authToken;


    public EventRR(String authToken){
        this.authToken = authToken;

    }

    /**
     * Return all the family members with the associated user
     * @return the list of family members
     */
    public EventRRResponse getEvents() throws DataAccessException {
        Database data = new Database();
        Connection connection = data.openConnection();
        //Check the Auth match
        AuthorizationDao authorizationDao = new AuthorizationDao(connection);
        String username = authorizationDao.findUser(authToken);
        if (username == null){
            data.closeConnection(false);
            return new EventRRResponse("Error:No authtoken matching with anyone!",false);
        }
        //Check the person match
        EventDao eventDao = new EventDao(connection);
        eventData = eventDao.listEvents(username);
        if (eventData.size() == 0){
            data.closeConnection(false);
            return new EventRRResponse("Error: No events associated with this username", false);
        }
        String serializedData = serializeData();
        data.closeConnection(true);
        EventRRResponse event = new EventRRResponse(eventData, true);
        return event;
    }


    private String serializeData(){
        //Add all people to a class to serialize them
        EventListData serialize = new EventListData();
        for (Event event : eventData){
            serialize.add(event);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(serialize);
    }}
