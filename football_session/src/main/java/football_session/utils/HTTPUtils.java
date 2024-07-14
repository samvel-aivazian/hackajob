package football_session.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPUtils {

    private HTTPUtils() {}

    public static String fetchResponseBody(final String url) {
        try {
            final URI uri = URI.create(url);
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch response body by URL: '" + url + "'");
        }
    }

}
