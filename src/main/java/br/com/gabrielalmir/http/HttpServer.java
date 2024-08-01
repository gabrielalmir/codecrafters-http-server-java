package br.com.gabrielalmir.http;

import br.com.gabrielalmir.http.socket.ServerSocketFactory;
import br.com.gabrielalmir.http.handler.RequestHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {
    private final Logger logger;
    private final int port;
    private final ServerSocketFactory socketFactory;
    private final RequestHandler requestHandler;
    private boolean running;

    public HttpServer(int port, ServerSocketFactory socketFactory, RequestHandler requestHandler) {
        this.port = port;
        this.socketFactory = socketFactory;
        this.requestHandler = requestHandler;
        this.running = true;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    public void start() {
        try (var serverSocket = socketFactory.createServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (running) {
                running = !Thread.currentThread().isInterrupted();
                requestHandler.handle(serverSocket);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
