package Handler;

import DataAccess.DataAccessException;
import Request.LoginRequest;
import Response.UserLoginResponse;
import Service.UserLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;


public class UserLoginHandle implements HttpHandler {

    @Override
    //Set up hanlder for Login
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        InputStream reqBody;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                //Set up the Vars for the handle
                reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                //Set up the gson translation
                Gson gson = new Gson();
                LoginRequest userRequest = gson.fromJson(reqData, LoginRequest.class);
                UserLogin userLog = new UserLogin(userRequest);
                UserLoginResponse response = userLog.loginUser();
                System.out.println(reqData);
                OutputStream resBody;
                String responseData;
                Gson gsonT = new GsonBuilder().setPrettyPrinting().create();

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
                //Send and write the response back
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
