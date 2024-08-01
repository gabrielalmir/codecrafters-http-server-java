package e2e;

import br.com.gabrielalmir.http.HttpServer;
import br.com.gabrielalmir.http.handler.ApplicationHandler;
import br.com.gabrielalmir.http.socket.DefaultServerSocketFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
class CreateHTTPServerTest {
    private Thread serverThread;

    @BeforeEach
    void setUp() {
        var requestHandler = new ApplicationHandler();
        var serverSocketFactory = new DefaultServerSocketFactory();
        var server = new HttpServer(8080, serverSocketFactory, requestHandler);
        serverThread = new Thread(server::start);
        serverThread.start();
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
        Assertions.assertEquals(200, responseCode);
    }

    @Test
    void shouldRespondNotFound() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/abc").toURL();
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        var responseCode = connection.getResponseCode();
        Assertions.assertEquals(404, responseCode);
    }

    @Test
    void shouldRespondWithBody() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/echo/abc").toURL();
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        var responseCode = connection.getResponseCode();
        assert(responseCode == HttpURLConnection.HTTP_OK);
        var responseBody = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Assertions.assertEquals("abc", responseBody);
    }
}
