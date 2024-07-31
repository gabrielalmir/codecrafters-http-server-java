package http.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class DefaultServerSocketFactory implements ServerSocketFactory {
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
