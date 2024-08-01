package e2e;

import http.HttpServer;
import http.handler.ApplicationHandler;
import http.socket.DefaultServerSocketFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
public class CreateHTTPServerTest {
    private Thread serverThread;

    @BeforeEach
    void setUp() throws InterruptedException {
        var requestHandler = new ApplicationHandler();
        var serverSocketFactory = new DefaultServerSocketFactory();
        var server = new HttpServer(8080, serverSocketFactory, requestHandler);
        serverThread = new Thread(server::start);
        serverThread.start();
        Thread.sleep(500);
        System.out.println("HTTP Server started");
    }

    @AfterEach
    void tearDown() {
        System.out.println("HTTP Server stopped");
        serverThread.interrupt();
    }

    @Test
    void shouldRespondOK() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/").toURL();
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        var responseCode = connection.getResponseCode();
        assert(responseCode == HttpURLConnection.HTTP_OK);
    }

    @Test
    void shouldRespondNotFound() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/abc").toURL();
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        var responseCode = connection.getResponseCode();
        assert(responseCode == HttpURLConnection.HTTP_NOT_FOUND);
    }

    @Test
    void shouldRespondWithBody() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/echo/abc").toURL();
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        var responseCode = connection.getResponseCode();
        assert(responseCode == HttpURLConnection.HTTP_OK);
        var responseBody = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assert(responseBody.equals("abc"));
    }
}
