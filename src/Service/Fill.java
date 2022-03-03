package Service;

import DataAccess.*;
import Model.Person;
import Response.fillResponse;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;

/**
 * Fills users family tree with certain up to 4 generations
 */
public class Fill {

    private fillResponse response;
    private Connection connect;
    Database data = new Database();
    private int numNewPeople;

    public int getNumNewPeople() {
        return numNewPeople;
    }

    public int getNumNewEvents() {
        return numNewEvents;
    }

    private int numNewEvents;

    public void Fill() throws DataAccessException {
        connect = data.openConnection();
    }


    /**
     * Populates the people and fills the data base with new people
     * If there is already info associated with that username, remove it
     * @param username associated with the people
     */
    public void clearData(String username) throws DataAccessException {
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
    public void populatePeople(String username, int generations) throws DataAccessException {
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
            response = new fillResponse("Successfully added " + numNewPeople + " persons and " + numNewEvents + " events to the database.", true);


        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            response = new fillResponse(ex.getMessage(), false);
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


    public fillResponse getResponse() {
        return response;
    }

}
