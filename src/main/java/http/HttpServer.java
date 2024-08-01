package http;

import http.handler.RequestHandler;
import http.socket.ServerSocketFactory;

import java.io.IOException;

public class HttpServer {
    private final int port;
    private final ServerSocketFactory socketFactory;
    private final RequestHandler requestHandler;
    private boolean running;

    public HttpServer(int port, ServerSocketFactory socketFactory, RequestHandler requestHandler) {
        this.port = port;
        this.socketFactory = socketFactory;
        this.requestHandler = requestHandler;
        this.running = true;
    }

    public void start() {
        try (var serverSocket = socketFactory.createServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (running) {
                running = !Thread.currentThread().isInterrupted();
                System.out.println("Waiting for connection...");
                requestHandler.handle(serverSocket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
