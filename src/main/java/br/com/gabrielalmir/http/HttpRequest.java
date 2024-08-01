package br.com.gabrielalmir.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> getHeaders() {
        var headers = new HashMap<String, String>();
        for (String header : requestArgs) {
            headers.put(header.split(":")[0], header.split(":")[1]);
        }
        return headers;
    }
}
