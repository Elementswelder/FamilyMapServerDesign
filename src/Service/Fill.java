package Service;

import DataAccess.*;
import Model.Person;
import Response.FillResponse;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;

/**
 * Fills users family tree with certain up to 4 generations
 */
public class Fill {

    private FillResponse response;
    private Connection connect;
    Database data = new Database();
    String[] URI;
    private int numNewPeople;

    public int getNumNewPeople() {
        return numNewPeople;
    }

    public int getNumNewEvents() {
        return numNewEvents;
    }

    private int numNewEvents;
    //Get the data and assign variables
    public void Fill(String[] array) throws DataAccessException {
        connect = data.openConnection();
        URI = array;

    }
    //Start filling the database, check to make sure the data is valid first
    public FillResponse startFilling() throws DataAccessException {
        String username;
        int generations;
        //Check to make sure the URI is correct
        if (URI.length <= 1){
            data.closeConnection(false);
            return new FillResponse("Error:URI is invalid", false);
        }
        username = URI[2];
        UserDao dao = new UserDao(connect);
        if (!dao.searchUsername(username)){
            data.closeConnection(false);
            return new FillResponse("That user does not exist in the database", false);
        }
        if (URI.length == 4){
            generations = Integer.parseInt(URI[3]);
            if (generations < 1){
                data.closeConnection(false);
                return new FillResponse("Error:The generation call is invalid", false);
            }
            username = URI[2];
        } else {
            generations = 4;
            username = URI[2];
        }
        try {
            clearData(username);
        } catch (DataAccessException ex){
            data.closeConnection(false);
            return new FillResponse("Error:Internal Server error, could not clear", false);
        }
        try{
            populatePeople(username, generations);
        } catch (DataAccessException ex) {
            data.closeConnection(false);
            return new FillResponse("Error:Could not populate the people in the database", false);
        }
        data.closeConnection(true);
        return new FillResponse("Successfully added " + numNewPeople + " persons and " + numNewEvents +
                " events to the database.", true);
    }


    /**
     * Populates the people and fills the data base with new people
     * If there is already info associated with that username, remove it
     * @param username associated with the people
     */
    private void clearData(String username) throws DataAccessException {
        try {
            PersonDao pDAO = new PersonDao(connect);
            EventDao eventDao = new EventDao(connect);
            pDAO.clearAssociatedUsername(username);
            eventDao.clearAssociatedUsername(username);

        } catch (DataAccessException e) {
            e.printStackTrace();

            throw new DataAccessException("Could not clear the username from PDAO in Fill");
        }


    }

    /**
     * Populate people as the previous function but have an optional generation number
     * @param username the username to populate
     * @param generations the amount of generations to populate
     */
    private void populatePeople(String username, int generations) throws DataAccessException {
        try {
            UserDao dao = new UserDao(connect);
            PersonDao pDAO = new PersonDao(connect);
            GenerateData generateData = new GenerateData(dao.findUser(username), generations + 1, connect);
            List<Person> personList = generateData.getListOfPeople();
            System.out.println("Made people");
            for (int i = 0; i  <personList.size(); i++){
                 pDAO.insertPerson(personList.get(i));
             }
            
            numNewPeople = generateData.getNumPeople();
            numNewEvents = numNewPeople * 3;
            response = new FillResponse("Successfully added " + numNewPeople + " persons and " + numNewEvents + " events to the database.", true);


        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            response = new FillResponse(ex.getMessage(), false);
            throw new DataAccessException("FILE NOT FOUND IN Fill Service populate people");
        }


    }

    public boolean headerCheck(String[] URI) throws DataAccessException {
        if (URI.length <= 1){
            throw new DataAccessException("Fill Header does not contain correct paramenter");
        }
        String username = URI[2];
        UserDao dao = new UserDao(connect);
        if (!dao.searchUsername(username)){
            throw new DataAccessException("That username is not in the database!");
        }

        return true;
    }


    public void closeDatabase() throws DataAccessException {
        data.closeConnection(true);
    }
    public void closeDatabaseFail() throws DataAccessException {
        data.closeConnection(false);
    }


    public FillResponse getResponse() {
        return response;
    }

}
