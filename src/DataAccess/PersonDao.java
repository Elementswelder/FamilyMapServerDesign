package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Model.Person;

/**
 * Person Data Object class that controls the communication between the Person Object and the Database
 */

public class PersonDao {

    private final Connection connect;



    /**
     * Sets the connection to the DataAccess.Database
     * @param connect the connection PORT/URL?
     */
    public PersonDao(Connection connect){
        this.connect = connect;
    }

    /**
     * Insert a new person into the Database
     * @param peep the person being inserted
     * @throws DataAccessException an error if inserting is unsuccessful
     */

    public void insertPerson(Person peep) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, peep.getPersonID());
            statement.setString(2, peep.getUsername());
            statement.setString(3, peep.getFirstName());
            statement.setString(4, peep.getLastName());
            statement.setString(5, peep.getGender());
            statement.setString(6, peep.getFatherID());
            statement.setString(7, peep.getMotherID());
            statement.setString(8,peep.getSpouseID());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error while inserting into the database");
        }



    }

    /**
     * Look for a person within the database
     * @param personID The ID of the person you're looking for
     * @return Return the found person (if found)
     * @throws DataAccessException throw is error in accessing the database
     */

    public Person findPerson(String personID) throws DataAccessException {
        Person person;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, personID);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"),rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
            //Return null instead of the exception
            else if (rs.wasNull()){
                throw new DataAccessException("There are no People associated with that username!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding person");
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
     * Return a list of people
     * @param theUser the ID of the user that is associated with the
     * @return The list of Persons attached to a certain user
     */
    public Set<Person> listOfPersons(String theUser) throws DataAccessException {
        Set<Person> listOfPersons = new HashSet<>();
        Person person;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, theUser);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
            while(rs.next()){
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                listOfPersons.add(person);
            }
            return listOfPersons;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding the list of PERSON");
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

    /**
     * Removes a single person from the database
     * @param username the ID of the person to remove
     * @throws DataAccessException throw if there is no access to the database
     */

    public void clearAssociatedUsername(String username) throws DataAccessException{
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";

        try (PreparedStatement statement = connect.prepareStatement(sql)){
            //Don't pass the sql into the statement twice;
            statement.setString(1,username);
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while clearing " + username + " people from database");
        }



    }

    public Set<Person> getAllPeople() throws DataAccessException {
        Set<Person> listOfPersons = new HashSet<>();
        Person person;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Person";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            //statement.setString(1, theUser);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
            while(rs.next()){
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                listOfPersons.add(person);
            }
            return listOfPersons;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding the list of PERSON");
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

    /**
     * Clears the person table from the database
     * @throws DataAccessException throws the appropriate error if it cannot connect
     */
    public void clearPersonTable() throws DataAccessException {
        String sql = "DELETE from Person";

        try (PreparedStatement statement = connect.prepareStatement(sql)){
            //Don't pass the sql into the statement twice;
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while clearing Person table");
        }

    }
    public boolean searchPersonID(String id) {
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)){
            statement.setString(1, id);

            rs = statement.executeQuery();

            if (rs.next()){
                rs.close();
                return true;

            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        return true;
    }


}
