package Handler;

import DataAccess.DataAccessException;
import DataAccess.GenerateData;
import Request.userRegisterRequest;
import Response.fillResponse;
import Response.userRegisterResponse;
import Service.Fill;
import Service.userRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class fillHandle implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //Setup the Vars for the handle
                String URI = exchange.getRequestURI().toString();
                String[] URIArray = URI.split("/");
                Fill fill = new Fill();
                fill.Fill();
                //Check to make sure there is a user in the URI
                if (fill.headerCheck(URIArray)){
                    int generations;
                    String username;
                    if (URIArray.length == 4){
                        generations = Integer.parseInt(URIArray[3]);
                        username = URIArray[2];
                    } else {
                        generations = 4;
                        username = URIArray[2];
                    }

                    //Clear out data associated with the username
                    fill.clearData(username);

                    //Fill the generations
                    fill.populatePeople(username, generations);


                    fill.closeDatabase();

                }

                fillResponse response = fill.getResponse();
                OutputStream resBody;
                String responseData;
                if (response.isValidate()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    resBody = exchange.getResponseBody();
                    responseData =
                            "{ \"message\": \"" + response.getMessage() +"\"," +
                                    "\"success\":\"true\" } ";

                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    resBody = exchange.getResponseBody();
                    responseData =
                            "{ \"message\": \"" + response.getMessage() +"\"," +
                                    "\"success\":\"false\" } ";

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
        catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            ex.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    }

    private String readString(InputStream is) throws IOException {
        StringBuilder stringBuild = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0 ){
            stringBuild.append(buf,0,len);
        }
        return stringBuild.toString();
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        // BufferedWriter bw = new BufferedWriter(sw);
        sw.write(str);
        sw.flush();
    }

}
