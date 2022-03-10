package DataAccess;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Class for the User Object
 */
public class UserDao {

    private final Connection connect;

    /**
     * User constructor to set the connection for the transfer
     * @param connect the connection URL
     */
    public UserDao(Connection connect) {

        this.connect = connect;
    }

    /**
     * Create a user class that creates a user
     * @param user the user that is being added
     * @throws DataAccessException if data is not valid
     */
    public String insertUser(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonID());

            statement.executeUpdate();

            return UUID.randomUUID().toString();
        } catch (SQLException ex) {
            throw new DataAccessException("Error while inserting into the database");
        }

    }

    /**
     * Search and look for a certain userID and confirm that it exists
     * @param username The userID that will be searched for
     * @return return the User that it finds
     * @throws DataAccessException throw this error if there is trouble getting into the database
     */
    public User findUser(String username) throws DataAccessException {
        User user;
        //Result set is the result of the Find.
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            //RS is taking the result of the search after the Query is executed
            rs = statement.executeQuery();
            //If RS Exists
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        rs.getString("firstName"),rs.getString("lastName"), rs.getString("gender"), rs.getString("personID"));
                return user;
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

    /**
     * List of all users connected to a personID
     * @param username the username that will look for the people with
     * @return Return a Set of all users that are connected
     * @throws DataAccessException Throw this if the connection did not work
     */
        public Set<User> listOfUsers(String username) throws DataAccessException {
            Set<User> listOfUsers = new HashSet<>();
            User user;
            //Result set is the result of the Find.
            ResultSet rs = null;
            String sql = "SELECT * FROM User WHERE username = ?;";
            try (PreparedStatement statement = connect.prepareStatement(sql)) {
                statement.setString(1, username);
                //RS is taking the result of the search after the Query is executed
                rs = statement.executeQuery();
                //If RS Exists
                while(rs.next()){
                    user = new User(rs.getString("username"), rs.getString("password"),
                            rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("gender"), rs.getString("personID"));
                    listOfUsers.add(user);
                }
                return listOfUsers;

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
     * Search for and Return a Set of all Users in the Database
     * @return return the Set of all Users
     * @throws DataAccessException throw this if connection is stopped
     */
    public Set<User> allUsers() throws DataAccessException {
            Set<User> listOfUsers = new HashSet<>();
            User user;
            //Result set is the result of the Find.
            ResultSet rs = null;
            String sql = "SELECT * FROM User";
            try (PreparedStatement statement = connect.prepareStatement(sql)) {
                //RS is taking the result of the search after the Query is executed
                rs = statement.executeQuery();
                //If RS Exists
                while(rs.next()){
                    user = new User(rs.getString("username"), rs.getString("password"),
                            rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("gender"), rs.getString("personID"));
                    listOfUsers.add(user);
                }
                return listOfUsers;

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
     * Clear all the users that are in the database
     * @throws DataAccessException Throws if there is an error in clearing
     */
    public void clearUser() throws DataAccessException {
        String sql = "DELETE from User";

        try (PreparedStatement statement = connect.prepareStatement(sql)){
            //Don't double pass the SQL into the statement twice
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Error while clearing the User table");
        }

    }

    public boolean searchUsername(String username) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)){
            statement.setString(1, username);

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
        return false;
    }

    public boolean searchAccount(String username, String password) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)){
            statement.setString(1, username);
            statement.setString(2, password);

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
        return false;
    }
}
