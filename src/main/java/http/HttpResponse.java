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

    public void send(String message, int statusCode) throws IOException {
        var head = this.getHttpStatus(statusCode);
        var body = this.getResponseBody(message, "text/plain");
        var output = "%s%s".formatted(head, body);

        outputStream.write(output.getBytes());
        outputStream.flush();
    }

    private String getHttpStatus(int statusCode) {
        var status = this.getStatusCode(statusCode);
        return "HTTP/1.1 %d %s\r\n".formatted(statusCode, status);
    }

    private String getStatusCode(int statusCode) {
        var status = statusCodeMessages.get(String.valueOf(statusCode));
        return status == null ? "" : status;
    }

    private String getResponseBody(String message, String contentType) {
        return "Content-Type: %s\r\nContent-Length: %s\r\n\r\n%s".formatted(contentType, message.length(), message);
    }
}
