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

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")){
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
                //Return 404 Page
                else if (urlPath.equals("/HTML/404.html")){
                    checkFile(urlPath, exchange);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    String filePathFail = "web/HTML/404.html";
                    File newFileFail = new File(filePathFail);
                    OutputStream resBody = exchange.getResponseBody();
                    Files.copy(newFileFail.toPath(), resBody);
                    exchange.getResponseBody().close();
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
    }

}
