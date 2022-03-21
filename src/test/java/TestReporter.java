import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestReporter {
    private MockWebServer mockWebServer;


    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }


    @Test
    public void onFailure() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("failure!"));
        String url = mockWebServer.url("").toString();
        urlReport report = ReportTool.getReport(url);
        int toTest = report.getLength();
        int status = report.getStatus();
        Assert.assertEquals(8, toTest);
        Assert.assertEquals(500, status);
    }

    @Test
    public void onSuccessful() throws IOException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200).
                setBody("successful"));
        String url = mockWebServer.url("/").toString();
        urlReport report = ReportTool.getReport(url);
        int toTest = report.getLength();
        int status = report.getStatus();
        Assert.assertEquals(10, toTest);
        Assert.assertEquals(200, status);
    }
}
