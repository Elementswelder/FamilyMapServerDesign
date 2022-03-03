package Response;

import Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Class based on the /event API call and retrieves a list of events
 */
public class eventRRResponse {
    private List<Event> eventList = new ArrayList<>();
    boolean validate;
    String message;



    /**
     * Gets all events that are in the database
     * @param the username associated with the events
     * @return all of the events that have to do with the username
     */
    public List<Event> getAllEvents(String username){

        return null;
    }
}
