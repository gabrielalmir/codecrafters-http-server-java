package br.com.gabrielalmir.http.handler;

import java.io.IOException;
import java.net.ServerSocket;

public interface RequestHandler {
    void handle(ServerSocket serverSocket) throws IOException;
}
