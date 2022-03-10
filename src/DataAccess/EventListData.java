package DataAccess;

import Model.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//Set up the event list for the GSON import
public class EventListData {

    private List<Event> events;

    public EventListData() {
        events = new ArrayList<>();
    }

    public void add(Event event) {

        events.add(event);
    }

    public Collection<Event> getItems() {
        return Collections.unmodifiableCollection(events);
    }
}
