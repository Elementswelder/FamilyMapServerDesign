package Service;

import Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Class based on the /event API call and retrieves a list of events
 */
public class eventRR {
    private List<Event> eventList = new ArrayList<>();
    boolean validate;


    /**
     * Validates if the request was valid or not
     * @return strings the true or false
     */
    public String success(){

        return null;
    }

    /**
     * Returns an error if the body was not sucsessful
     * @return the error as a string
     */
    public String error(){
        return null;
    }
}
