import org.json.JSONWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ReportTool {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the URLs");
        for (Scanner it = scanner; it.hasNext(); ) {
            String line = it.next();
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(line))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String url = response.uri().toString();
                int status = response.statusCode();
                int length = response.body().length();
                urlReport urlReportInstance = new urlReport(url, status, length);

                JSONWriter jsonWriter = new JSONWriter(System.out);
                jsonWriter.object();
                jsonWriter.key("url");
                jsonWriter.value(urlReportInstance.getUrl());
                jsonWriter.key("status");
                jsonWriter.value(urlReportInstance.getStatus());
                jsonWriter.key("length");
                jsonWriter.value(urlReportInstance.getLength());
                jsonWriter.endObject();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

