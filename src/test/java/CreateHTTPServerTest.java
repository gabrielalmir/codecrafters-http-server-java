import http.HttpServer;
import http.handler.RequestHandler;
import http.socket.ServerSocketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateHTTPServerTest {

    @Mock
    ServerSocketFactory serverSocketFactory;

    @Mock
    RequestHandler requestHandler;

    @Mock
    ServerSocket serverSocket;

    @Mock
    Socket socket;

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        when(serverSocketFactory.createServerSocket(anyInt())).thenReturn(serverSocket);
        server = new HttpServer(8080, serverSocketFactory, requestHandler);
    }

    @Test
    void bindAPort() throws IOException {
        Thread thread = new Thread(server::start);
        thread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        verify(requestHandler, atLeastOnce()).handle(serverSocket);
        thread.interrupt();
    }
}
