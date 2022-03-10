package Handler;

import DataAccess.DataAccessException;
import Request.LoadRequest;
import Response.LoadResponse;
import Service.Load;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class LoadHandle implements HttpHandler {

    @Override
    //Set up the handle for Load
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                //Setup the Vars for the handle
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                LoadResponse response;
                System.out.println(reqData);
                Gson gson = new Gson();
                LoadRequest loadTwo = gson.fromJson(reqData, LoadRequest.class);
                //Clears the Database with Clear
                Load load = new Load(loadTwo);
                response = load.startLoading();

                OutputStream resBody;
                String responseData;
                Gson gsonT = new GsonBuilder().setPrettyPrinting().create();
                if (response.isValidate()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                //Send and write the response back
                resBody = exchange.getResponseBody();
                responseData = gsonT.toJson(response);
                writeString(responseData, resBody);
                System.out.println(responseData);
                reqBody.close();

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
        sw.write(str);
        sw.flush();
    }

}
