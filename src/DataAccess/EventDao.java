package DataAccess;

import Model.Event;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Creates and allows modification for the Event Object
 */
public class EventDao {

    private final Connection connect;
    private String connectionURL = "jdbc:sqlite:database.sqkute";

    /**
     * A constructer to connecect to the database
     * @param connect the connection to the database
     */

    public EventDao(Connection connect){
        this.connect = connect;
    }

    /**
     * Insert a new even into the database
     * @param event The Event to Add
     * @throws DataAccessException Throw this if there is no access
     */
    public void insert(Event event) throws DataAccessException {

        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, event.getEventID());
            statement.setString(2, event.getAssociatedUsername());
            statement.setString(3, event.getPersonID());
            statement.setFloat(4, event.getLatitude());
            statement.setFloat(5, event.getLongitude());
            statement.setString(6, event.getCountry());
            statement.setString(7, event.getCity());
            statement.setString(8, event.getEventType());
            statement.setInt(9, event.getYear());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error while inserting into the database");
        }

    }

    /**
     * Find an event within the data base
     * @param eventID The ID to identify the event
     * @return return the found Event if any
     * @throws DataAccessException Throw this if you are unable to connect
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        //Resultset is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, eventID);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exsists
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding event");
            //Finally meaning do this no matter what
        } finally {
            //Close the results
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * Removes a single event from the database
     * @param eventID the ID of the event to remove
     * @throws DataAccessException throw if there is no access to the database
     */

    public void clearAssociatedUsername(String username) throws DataAccessException{
        String sql = "DELETE FROM Event WHERE associatedUsername = ?;";

        try (PreparedStatement statement = connect.prepareStatement(sql)){
            //Don't pass the sql into the statement twice;
            statement.setString(1,username);
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while clearing " + username + " event from database");
        }



    }

    /**
     * Clear out all the events if needed
     * @throws DataAccessException Throw if unable to connect
     */
    public void clearEvent() throws DataAccessException {
        String sql = "DELETE FROM Event";

        try (PreparedStatement statement = connect.prepareStatement(sql)){
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while clearing Event");
        }


    }

    /**
     * A method that calls and returns all of the events for all family memebrs under a certin user
     * @param personID The userID to find the events
     * @return return the list of events
     * @throws DataAccessException Throw if you cannot access the data
     */
    public Set<Event> listEvents(String personID) throws DataAccessException{
        Set<Event> listOfEvents = new HashSet<>();
        Event event;
        //Resultset is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE personID = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, personID);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exsists
            while(rs.next()){
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                listOfEvents.add(event);
            }
            return listOfEvents;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding the list of events");
            //Finally meaning do this no matter what
        } finally {
            //Close the results
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }




}

//CRUD  CREATES, RECIEVES, U?, DELETE FOR ALL DOAS CLASS
//DAO connects directly to the DataAccess.Database
