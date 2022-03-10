package Handler;

import DataAccess.DataAccessException;
import Response.PersonIDResponse;
import Service.PersonID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class PersonIDHandle implements HttpHandler {

    @Override
    //Set up handler for specific person ID
    public void handle(HttpExchange exchange) throws IOException {
        PersonIDResponse response = null;
        boolean success = false;
        Headers reqHeaders = exchange.getRequestHeaders();

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                if (reqHeaders.containsKey("Authorization")) {
                    //Setup the Vars for the handle
                    String authToken = reqHeaders.getFirst("Authorization");
                    String URI = exchange.getRequestURI().toString();
                    String[] URIArray = URI.split("/");
                    PersonID getPerson = new PersonID(authToken, URIArray[2]);
                    response = getPerson.findUser();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();


                    OutputStream resBody;
                    String responseData;
                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        resBody = exchange.getResponseBody();
                        response.setMessageNull();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        resBody = exchange.getResponseBody();
                        response.setDataNull();
                    }
                    responseData = gson.toJson(response);


                    writeString(responseData, resBody);
                    System.out.println(responseData);
                    resBody.close();

                    exchange.getResponseBody().close();

                    success = true;

                }

                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }

            }
        } catch (DataAccessException e) {
            e.printStackTrace();

        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
