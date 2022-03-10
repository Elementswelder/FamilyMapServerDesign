package Service;
import DataAccess.DataAccessException;
import DataAccess.Database;

/**
 * The Class that will call all DOAs to clear
 */
public class Clear {
    //Result of the success
    private String result;
    /**
     * Clear all the DOAS
     */
    public Clear(){};

    public boolean clear(){
        //Try to open the database and clear everything in the database
        try {
            Database data = new Database();
            data.openConnection();
            result = data.clearTables();
            if (result.contains("Clear")){
                data.closeConnection(true);
                return true;
            }
            else{
                data.closeConnection(false);
                return false;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return false;

    }

    public String getResult() {
        return result;
    }
}
