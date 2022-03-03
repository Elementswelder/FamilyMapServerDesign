import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.Locale;
import java.io.File;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")){
                String filePath;
                String urlPath = exchange.getRequestURI().toString();

                //Return the index.html (main)
                if (urlPath.equals("/") || urlPath == null){
                    urlPath = "/index.html";
                    checkFile(urlPath, exchange);

                }
                //Return the Favicon icon
                else if (urlPath.equals("/favicon.ico")){
                    checkFile(urlPath, exchange);

                }
                //Return the css.main
                else if (urlPath.equals("/css/main.css")){
                    checkFile(urlPath, exchange);
                }
                //Return the background
                else if (urlPath.equals("/img/background.png")){
                    checkFile(urlPath, exchange);
                }
                //Return the favicon.jpg
                else if (urlPath.equals("/favicon.jpg")){
                    checkFile(urlPath, exchange);
                }

            }
        }
        catch (IOException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }



    }

    private void checkFile(String urlPath, HttpExchange exchange) throws IOException {
        String filePath = "web" + urlPath;
        File newFile = new File(filePath);

        if (newFile.exists()){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            OutputStream resBody = exchange.getResponseBody();
            Files.copy(newFile.toPath(), resBody);
            resBody.close();

        }
        else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
        }

    }

    //ONLY ACCEPT GET REQUEST

    //IF URLPATH IS NULL OR / SET URLPATH TO /INDEX.HTML
    //STRING URLPATH HTTPEXCHANGE.GETREQUESTURI().TOSTRING());
    //String Filepath = "web" +urlpath;
    //Return a 404 if file not found or if it does not exisst
    //If it does exsist read the file and write it to the HTTPEXAHNGE output stream
    //OutputStream reBody = exhange.getRepsoneBdoy();
    //Files.copy(file.toPath(), respBody);

}
