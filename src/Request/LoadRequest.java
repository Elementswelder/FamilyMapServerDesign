package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Clears then loads all the information into the database
 */
public class LoadRequest {

    private List<User> listUsers = new ArrayList<User>();
    private List<Person> listPersons = new ArrayList<Person>();
    private List<Event> listEvents = new ArrayList<Event>();

    /**
     * Contructor to create from the body reqeust
     * @param users the list of userse
     * @param people the list of people
     * @param events the list of events
     */
    public LoadRequest(List<User> users, List<Person> people, List<Event> events){
        this.listUsers = users;
        this.listPersons = people;
        this.listEvents = events;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public List<Person> getListPersons() {
        return listPersons;
    }

    public List<Event> getListEvents() {
        return listEvents;
    }
}
