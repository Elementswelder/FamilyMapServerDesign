package Request;

import Model.Event;
import Model.Person;
import Model.User;

public class LoadRequest {

    private User[] users = null;
    private Event[] events = null;
    private Person[] persons = null;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    LoadRequest(User[] user, Event[] event, Person[] people){
        users = user;
        events = event;
        persons = people;
    }


}
