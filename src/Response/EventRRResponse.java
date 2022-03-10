package Response;

import Model.Event;
import java.util.HashSet;
import java.util.Set;

/**
 * Class based on the /event API call and retrieves a list of events
 */
public class EventRRResponse {
    private Set<Event> data = new HashSet<>();
    private String message;
    boolean success;

    /**
     * Error constructor when it fails
     * @param data the message associated with the error
     * @param test failure
     */
    public EventRRResponse(Set<Event> data, boolean test){
        this.data = data;
        this.success = test;
    }

    public EventRRResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public boolean isValidate() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Set<Event> getData(){
        return data;
    }

    public void SetDataNull(){
        this.data = null;
    }
}
