import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            var reader = new BufferedReader(new InputStreamReader(inputStream));

            System.out.println(reader.readLine());

            var outputStream = socket.getOutputStream();
            outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
