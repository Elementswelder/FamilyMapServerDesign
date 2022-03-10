package DataAccess;

import Model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PersonListData {

    private List<Person> people;
    private boolean success;

    public PersonListData() {
        people = new ArrayList<>();
    }

    public void add(Person person) {
        people.add(person);
    }

    public void setBool(boolean bool){
        success = bool;
    }
    //For the GSON to get Items
    public Collection<Person> getItems() {
        return Collections.unmodifiableCollection(people);
    }
}
