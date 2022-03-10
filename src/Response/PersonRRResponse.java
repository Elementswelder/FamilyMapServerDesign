package Response;

import Model.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * Class of the /person response
 */
public class PersonRRResponse {
    private Set<Person> data = new HashSet<Person>();
    private String message;
    boolean success;

    /**
     * Error constructor when it fails
     * @param message the message associated with the error
     * @param success failure
     */
    public PersonRRResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public PersonRRResponse(Set<Person> list, boolean success){
        this.data = list;
        this.success = success;
    }

    public Set<Person> getPersonData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageNull(){
        message = null;
    }

    public void setDataNull(){
        data = null;
    }
}
