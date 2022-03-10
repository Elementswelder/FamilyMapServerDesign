package Handler;

import DataAccess.DataAccessException;
import Response.PersonRRResponse;
import Service.PersonRR;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class PersonRRHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonRRResponse response = null;
        boolean success = false;
        Headers reqHeaders = exchange.getRequestHeaders();

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                if (reqHeaders.containsKey("Authorization")) {
                    //Setup the Vars for the handle
                    String authToken = reqHeaders.getFirst("Authorization");
                    PersonRR personList = new PersonRR(authToken);
                    response = personList.getFamily();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();


                    OutputStream resBody;
                    String responseData;
                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        resBody = exchange.getResponseBody();
                        response.setMessageNull();
                        responseData = gson.toJson(response);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        resBody = exchange.getResponseBody();
                        response.setDataNull();
                        responseData = gson.toJson(response);

                    }


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
