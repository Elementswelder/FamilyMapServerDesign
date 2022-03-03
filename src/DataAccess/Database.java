package DataAccess;

import java.sql.*;

/**
 * The Database class that controls the connection to the Database
 */
public class Database {
    private Connection conn = null;

    /**
     * Open the connection between the Code and the SQLlite Database
     * @return return the connection URL
     * @throws DataAccessException If the connection cannot be made, throw this exception
     */
    public Connection openConnection() throws DataAccessException {

        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            conn = DriverManager.getConnection(CONNECTION_URL);

            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");

        }

        return conn;
    }

    /**
     * Return the connection that was created in openConnection
     * @return return the connection URL
     * @throws DataAccessException if the connection cannot be made, throw the exception
     */
    //Check to make sure that the connection is actually opened
    public Connection getConnection() throws DataAccessException {
        if (conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    /**
     * Close the connection when the data is finished being moved around
     * @param commit Decide if we need to save the changes or not
     * @throws DataAccessException If the connection is interupted or stopped, throw an exception
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     * Clear all the tables in all the database
     * @throws DataAccessException If the connection cannot being completed, throw the exception
     */
    public String clearTables() throws DataAccessException {
        try (Statement statement = conn.createStatement()){
            String sql = "DELETE FROM Event";
            statement.executeUpdate(sql);
            sql = "DELETE FROM Person";
            statement.executeUpdate(sql);
            sql = "DELETE FROM Authorization";
            statement.executeUpdate(sql);
            sql = "DELETE FROM User";
            statement.executeUpdate(sql);
            return "Clear succeeded.";

        } catch (SQLException ex){
            //TODO: See if this is proper to return en error
            return "Error: " + ex.toString();
            // throw new DataAccessException("SQL Error when attempting to clear the tables");
        }
    }

}
