import http.handler.ApplicationHandler;
import http.HttpServer;
import http.socket.DefaultServerSocketFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running HTTP Server at port 4221 ...");
        var httpServer = new HttpServer(4221, new DefaultServerSocketFactory(), new ApplicationHandler());
        httpServer.start();
    }
}
