package Service;
import DataAccess.DataAccessException;
import DataAccess.Database;

/**
 * The Class that will call all DOAs to clear
 */
public class clear {

    private String result;
    /**
     * Clear all the DOAS
     */
    public boolean clear(){
        try {
            Database data = new Database();
            data.openConnection();
            String result = data.clearTables();
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
