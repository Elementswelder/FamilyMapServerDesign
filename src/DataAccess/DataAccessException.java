package DataAccess;

/**
 * What happens when the data is not able to be accessed by the server
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }
    DataAccessException(){
        super();
    }
}
