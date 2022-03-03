package DataAccess;

import Model.Authorization;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A class to create and access auth tokens for the users
 */
public class AuthorizationDao {
    private final Connection connect;
    /**
     * A method to create a unique auth token
     * @return Return said Auth token
     */  //InsertAuth
    //The createAuth would go in Service
    public AuthorizationDao(Connection connection){
        connect = connection;
    }

    public boolean insertUser(Authorization authorize) throws DataAccessException {
        String sql = "INSERT INTO Authorization (authtoken, username) VALUES(?,?)";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, authorize.getAuthToken());
            statement.setString(2, authorize.getUsername());

            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            throw new DataAccessException("Error while inserting into the database");
        }

    }


    public String createAuth() {
        return null;
    }

    /**
     * Verifies if the Authenitcation is actually valid
     * @param auth the Auth token
     * @param personID The person it is being compared to
     * @return Return true if it is verified
     */
    //getAuthToken
    public boolean verifyAuth(String auth, String personID){
        return false;
    }

    public Authorization findAuth(String username) throws DataAccessException {
        User user;
        //Resultset is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM Authorization WHERE username = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exsists
            if (rs.next()) {
                Authorization auth = new Authorization(rs.getString("username"), rs.getString("authtoken"));
                return auth;
            }
            //Return null here
            else if (rs.wasNull()) {
                throw new DataAccessException("That user does not exist!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while finding person");
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

    //Erase a single Auth Function

    /**
     * Clear the table for the Auth table and it's data
     */
    public void clearTable() {

    }
}


//CRUD  CREATES, RECIEVES, U?, DELETE FOR ALL DOAS CLASS
//DAO connects directly to the DataAccess.Database