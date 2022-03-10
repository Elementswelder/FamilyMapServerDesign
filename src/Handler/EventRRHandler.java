package Handler;

import DataAccess.DataAccessException;
import Response.EventRRResponse;
import Service.EventRR;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class EventRRHandler implements HttpHandler {

    @Override
    //Set up the handler for all Events
    public void handle(HttpExchange exchange) throws IOException {
        EventRRResponse response = null;
        boolean success = false;
        Headers reqHeaders = exchange.getRequestHeaders();

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                if (reqHeaders.containsKey("Authorization")) {
                    //Set up the Vars for the handle
                    String authToken = reqHeaders.getFirst("Authorization");
                    EventRR eventList = new EventRR(authToken);
                    response = eventList.getEvents();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();


                    OutputStream resBody;
                    String responseData;
                    if (response.isValidate()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        resBody = exchange.getResponseBody();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        resBody = exchange.getResponseBody();
                        response.SetDataNull();

                    }
                    //Send and write the response back
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
