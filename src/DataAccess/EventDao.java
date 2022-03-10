package DataAccess;

import Model.Event;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates and allows modification for the Event Object
 */
public class EventDao {

    private final Connection connect;
   // private final String connectionURL = "jdbc:sqlite:database.db";

    /**
     * A constructor to connect to the database
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
        //Insert a new event parameter into the database
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
            //If there is an error adding throw exception
            System.out.println("Error in EventDAO INSERT");
            throw new DataAccessException("Error while inserting into the database");
        }
    }

    /**
     * Find an event within the database
     * @param eventID The ID to identify the event
     * @return return the found Event if any
     * @throws DataAccessException Throw this if you are unable to connect
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, eventID);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
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
            //Finally, meaning do this no matter what
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
     * @param username the ID of the event to remove
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
     * A method that calls and returns all the events for all family members under a certain user
     * @param username The username to find the events
     * @return return the list of events
     * @throws DataAccessException Throw if you cannot access the data
     */
    public Set<Event> listEvents(String username) throws DataAccessException{
        Set<Event> listOfEvents = new HashSet<>();
        Event event;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
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
            //Finally, meaning do this no matter what
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

