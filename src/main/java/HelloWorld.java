import DataAccess.*;
import Model.Event;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class HelloWorld {
    public static void main(String args[]) throws DataAccessException {
        System.out.println("Hello world!");
        System.out.println("You can delete this file.");
        Database data = new Database();
        Connection con = data.getConnection();
        System.out.println("Got connection");
        EventDao eventNew = new EventDao(con);
        UserDao userNew = new UserDao(con);
        Event newEvent = new Event("38", "775", "dgj", 1923, 1043, "Germany", "Hamburg",
                "Birth", 2011);
        User newUser = new User("bobbb", "bad", "a@gmail.com", "Bobby", "Joe", "m", "abcdeee");

        System.out.println("Connected to Event");
        eventNew.insert(newEvent);
        System.out.println("Inserted New Event");
        userNew.insertUser(newUser);
        System.out.println("Inserted New User");
       // eventNew.clearEvent();
        System.out.println("Cleared just events");
        //data.clearTables();
        //System.out.println("Clear tables");
        data.closeConnection(true);
        System.out.println("Connection is closed");


    }
}
