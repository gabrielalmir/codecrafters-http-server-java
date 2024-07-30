package http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpResponse {
    private final OutputStream outputStream;
    private final HashMap<String,String> statusCodeMessages = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.statusCodeMessages.put("200", "OK");
        this.statusCodeMessages.put("404", "Not Found");
    }

    public void send(String string, int statusCode) throws IOException {
        var status = statusCodeMessages.get(String.valueOf(statusCode));
        var output = "HTTP/1.1 %d %s %s\r\n\r\n".formatted(statusCode, status, string);
        outputStream.write(output.getBytes());
        outputStream.flush();
    }
}
