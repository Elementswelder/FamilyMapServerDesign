package Handler;

import DataAccess.DataAccessException;
import Request.LoadRequest;
import Request.userRegisterRequest;
import Response.loadResponse;
import Response.userRegisterResponse;
import Service.Load;
import Service.userRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class LoadHandle implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                //Setup the Vars for the handle
                Headers reqHeaders = exchange.getRequestHeaders();
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                System.out.println(reqData);
                //Setup the gson translation
                Gson gson = new Gson();
                LoadRequest loadRequest = gson.fromJson(reqData, LoadRequest.class);
                //Clears the Database with Clear
                Load load = new Load();
                load.loadDatabase(reqData);

                loadResponse response = load.getResponse();
                OutputStream resBody;
                String responseData;
                if (response.isValidate()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    resBody = exchange.getResponseBody();
                    responseData =
                            "{ \"authtoken\": \"" +"\"," +
                                    "\"username\": \""  + "\"," +
                                    "\"personID\":\"" + "\"," +
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
        // BufferedWriter bw = new BufferedWriter(sw);
        sw.write(str);
        sw.flush();
    }

}