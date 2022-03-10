import java.io.*;
import java.net.*;


import DataAccess.*;
import com.google.gson.*;
import com.sun.net.httpserver.*;
import Handler.*;

public class Server {
    //Don't worry about for this class, but the number of connections in queue
    private static final int MAX_WAITING_CONNECTION = 12;

    private HttpServer server;

    //Only thing in args is the port number
    public static void main(String[] args) throws IOException {
        String portNum = args[0];
        new Server().run(portNum);
    }

    private void run(String portNum) throws IOException {

        System.out.println("Starting the HTTP Server");

        try {
            server = HttpServer.create(
                new InetSocketAddress(Integer.parseInt(portNum)), MAX_WAITING_CONNECTION);
            }
        catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        //Has to do with threading but not to worry about for this project
        server.setExecutor(null);

        //Load up the Location and Name Lists
        System.out.println("Setting up Data");
        setupData();

        System.out.println("Data loaded successfully!");

        //Create all the contexts for the server
        System.out.println("Creating Contexts");
        server.createContext("/user/register", new UserRegisterHandle());
        server.createContext("/user/login", new UserLoginHandle());
        server.createContext("/clear", new ClearHandle());
        server.createContext("/fill", new FillHandle());
        server.createContext("/load", new LoadHandle());
        server.createContext("/person/", new PersonIDHandle());
        server.createContext("/person", new PersonRRHandler());
        server.createContext("/event/", new EventIDHandler());
        server.createContext("/event", new EventRRHandler());
        server.createContext("/", new FileHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server Started");


    }

    private void setupData() throws IOException {
        Gson gson = new Gson();

        //Load the locations into Location Class
        File fileLocation = new File("json/locations.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
             LocationList.LocationListStatic locations = gson.fromJson(fileReader, LocationList.LocationListStatic.class);
             //NEEDED TO MAKE THE DATA STATIC, DO NOT REMOVE
             locations.setStatic();
             System.out.println("Loaded Locations");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLocatoin");
            ex.printStackTrace();
        }

        //Load the fNames into Firstname List
        fileLocation = new File("json/fnames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            FirstNameList.firstNameStatic fList = new FirstNameList.firstNameStatic(nameArr);
            System.out.println("Loaded First Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLocatoin");
            ex.printStackTrace();
        }

        //Load the last names
        fileLocation = new File("json/snames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            LastNameList.lastNameStatic lList = new LastNameList.lastNameStatic(nameArr);
            System.out.println("Loaded Last Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLastName");
            ex.printStackTrace();
        }

        //Load the middle names
        fileLocation = new File("json/mnames.json");
        try (FileReader fileReader = new FileReader(fileLocation)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject rootObj = (JsonObject)jsonParser.parse(fileReader);
            JsonArray nameArr = (JsonArray)rootObj.get("data");
            MiddleNameList.middleNameStatic mList = new MiddleNameList.middleNameStatic(nameArr);
            System.out.println("Loaded Middle Names");

        } catch (FileNotFoundException ex) {
            System.out.println("File could not be find in setupDataLastName");
            ex.printStackTrace();
        }

    }


}
