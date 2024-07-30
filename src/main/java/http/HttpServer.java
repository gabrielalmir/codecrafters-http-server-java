package http;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (var serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (!Thread.currentThread().isInterrupted()) { handleRequest(serverSocket); }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleRequest(ServerSocket serverSocket) {
        try (var socket = serverSocket.accept()) {
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
