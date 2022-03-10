package Handler;

import DataAccess.DataAccessException;
import Response.FillResponse;
import Service.Fill;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class FillHandle implements HttpHandler {

    @Override
    //Set up the handler for Fill
    public void handle(HttpExchange exchange) throws IOException {
        FillResponse response = null;
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                //Setup the Vars for the handle
                String URI = exchange.getRequestURI().toString();
                String[] URIArray = URI.split("/");
                Fill fill = new Fill();
                fill.Fill(URIArray);

                response = fill.startFilling();

                OutputStream resBody;
                String responseData;
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (response.isValidate()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                //Send and write the response back
                resBody = exchange.getResponseBody();
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

        } catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            ex.printStackTrace();
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
