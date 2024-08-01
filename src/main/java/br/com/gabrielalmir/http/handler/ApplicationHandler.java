package br.com.gabrielalmir.http.handler;

import br.com.gabrielalmir.http.HttpRequest;
import br.com.gabrielalmir.http.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationHandler implements RequestHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void handle(ServerSocket serverSocket) throws IOException {
        var socket = serverSocket.accept();

        logger.log(Level.INFO, "Accepted new connection");

        var inputStream = socket.getInputStream();
        var httpRequest = new HttpRequest(inputStream);
        var request = httpRequest.parseRequest();

        var outputStream = socket.getOutputStream();
        var response = new HttpResponse(outputStream);

        if (request.getUrl().equals("/")) {
            response.send("", 200);
        } else if (request.getUrl().startsWith("/echo/")) {
            var queryParam = request.getUrl().substring("/echo/".length());
            response.send(queryParam, 200);
        } else {
            response.send("", 404);
        }
    }
}
