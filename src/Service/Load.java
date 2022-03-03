package Service;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.loadResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Clears then loads all the information into the database
 */
public class Load {

    private loadResponse response;
    private LoadRequest request;
    private List<Event> eventList = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public Load() throws DataAccessException {
        boolean clearSuccess = clear();
        if (!clearSuccess){
            throw new DataAccessException("Unable to clear the database in Load");
        }
    }

    /**
     * Loads the data from request into the database
     * @param data the data that will be loaded into the database
     */
    public void loadDatabase(String data){

        JsonParser jsonParser = new JsonParser();

        JsonObject root = (JsonObject)jsonParser.parse(data);
        JsonArray personArray = (JsonArray) root.get("users");
        for (int i = 0; i < personArray.size(); i++){


        }



    }

    /**
     * Clear all the DOAS
     */
    public boolean clear(){
        clear newClear = new clear();
       boolean cleared =  newClear.clear();
       return cleared;


    }

    public loadResponse getResponse(){
        return response;
    }
}
