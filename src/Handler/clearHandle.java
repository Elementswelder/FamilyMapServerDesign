package Handler;

import DataAccess.DataAccessException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import Service.clear;
import Response.clearResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.Buffer;
import java.util.Locale;


public class clearHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                clear serviceClear = new clear();
                boolean clearSuccess = serviceClear.clear();
                String message = serviceClear.getResult();
                String responseData;
                OutputStream resBody;
                if (clearSuccess) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    resBody = exchange.getResponseBody();
                     responseData =
                            " { \"message\":\"Clear succeeded.\"," +
                                    "\"success\":true } ";
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                    resBody = exchange.getResponseBody();
                    responseData =
                            " { \"message\":\"Error\"" + message + "\"," +
                                    "\"success\":false } " ;
                }
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
       // BufferedWriter bw = new BufferedWriter(sw);
        sw.write(str);
        sw.flush();
    }
}


//SERVICE CLASSES RETURN A RESPONSE