package Handler;

import Response.ClearResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

import Service.Clear;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Locale;


public class ClearHandle implements HttpHandler {
    @Override
    //Set up the handler for clear
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        ClearResponse response;

        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                //Start the clear in the database
                Clear serviceClear = new Clear();
                boolean clearSuccess = serviceClear.clear();
                //Get the result message
                String message = serviceClear.getResult();
                String responseData;
                OutputStream resBody;
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                //If the clear() was successful, send an ok response, otherwise send 400
                if (clearSuccess) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    response = new ClearResponse("Clear succeeded.", true);
                    resBody = exchange.getResponseBody();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                    resBody = exchange.getResponseBody();
                    response = new ClearResponse("Error:" + message, false);
                }
                //Send and write the response back
                responseData = gson.toJson(response);
                writeString(responseData, resBody);
                System.out.println(responseData);
                resBody.close();

                success = true;
            }

            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                exchange.getResponseBody().close();
            }
            exchange.getResponseBody().close();
        } catch (IOException ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
