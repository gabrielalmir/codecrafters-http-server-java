package http.handler;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;

public class ApplicationHandler implements RequestHandler {
    @Override
    public void handle(ServerSocket serverSocket) throws IOException {
        var socket = serverSocket.accept();

        System.out.println("Accepted new connection");

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
