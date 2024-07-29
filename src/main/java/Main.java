import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Logs from your program will appear here!");

        try (var serverSocket = new ServerSocket(4221)) {
            serverSocket.setReuseAddress(true);

            try (var socket = serverSocket.accept()) {
                System.out.println("Accepted new connection");
                var outputStream = socket.getOutputStream();
                outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                outputStream.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
