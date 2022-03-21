import org.json.JSONWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ReportTool {
    // Get the reports for a list of URLs
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the URLs");
        while (scanner.hasNext()) {
            String line = scanner.next();
            urlReport report = getReport(line);
            JSONWriter json = new JSONWriter(System.out);
            json.object();
            json.key("url").value(report.getUrl());
            json.key("status").value(report.getStatus());
            json.key("length").value(report.getLength());
            json.endObject();
        }
    }
    // Get the report for a single URL
    public static urlReport getReport(String line) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(line))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String uri = response.uri().toString();
        int status = response.statusCode();
        int length = response.body().length();
        return new urlReport(uri, status, length);
    }
}
    class urlReport {
        private final String url;
        private final int status;
        private final int length;

        public String getUrl() {
            return url;
        }

        public int getStatus() {
            return status;
        }

        public int getLength() {
            return length;
        }

        public urlReport(String url, int status, int length) {
            this.url = url;
            this.status = status;
            this.length = length;
        }
    }

