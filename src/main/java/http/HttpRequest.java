package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final InputStream inputStream;
    private String[] requestArgs;

    public HttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest parseRequest() throws IOException {
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        this.requestArgs = reader.readLine().split(" ");
        return this;
    }

    public String getUrl() {
        return requestArgs[1];
    }
}
