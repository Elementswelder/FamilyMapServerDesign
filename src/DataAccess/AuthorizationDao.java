package DataAccess;

import Model.Authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class to create and access auth tokens for the users
 */
public class AuthorizationDao {

    private final Connection connect;

    /**
     * A method to create a unique auth token
     */
    //The createAuth would go in Service
    public AuthorizationDao(Connection connection){

        connect = connection;
    }

    //Insert a new Authtoken/User combo into the database based on the parameter
    public void insertUser(Authorization authorize) throws DataAccessException {
        String sql = "INSERT INTO Authorization (authtoken, username) VALUES(?,?)";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, authorize.getAuthToken());
            statement.setString(2, authorize.getUsername());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error while inserting into the database");
        }

    }
    //Find an authorization token based on the username parameter
    public Authorization findAuth(String username) throws DataAccessException {
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Authorization WHERE username = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
            if (rs.next()) {
                return new Authorization(rs.getString("username"), rs.getString("authtoken"));
            }
            //Return null here
            else if (rs.wasNull()) {
                throw new DataAccessException("That user does not exist!");
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

    //Find a user in the Authtoken table based on the authtoken parameter
    public String findUser(String authtoken) throws DataAccessException {
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Authorization WHERE authtoken = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, authtoken);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
            //Return null here
            else if (rs.wasNull()) {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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

}
