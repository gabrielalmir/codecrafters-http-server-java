package br.com.gabrielalmir.http.socket;

import java.io.IOException;
import java.net.ServerSocket;

public interface ServerSocketFactory {
    ServerSocket createServerSocket(int port) throws IOException;
}
