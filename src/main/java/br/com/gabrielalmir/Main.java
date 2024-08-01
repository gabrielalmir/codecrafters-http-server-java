package br.com.gabrielalmir;

import br.com.gabrielalmir.http.handler.ApplicationHandler;
import br.com.gabrielalmir.http.HttpServer;
import br.com.gabrielalmir.http.socket.DefaultServerSocketFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Running HTTP Server at port 4221 ...");
        var httpServer = new HttpServer(4221, new DefaultServerSocketFactory(), new ApplicationHandler());
        httpServer.start();
    }
}
