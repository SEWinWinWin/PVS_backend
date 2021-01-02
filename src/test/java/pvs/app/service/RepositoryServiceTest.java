package pvs.app.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import pvs.app.Application;
import pvs.app.dto.CodeCoverageDTO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RepositoryServiceTest {

    @Autowired
    private RepositoryService repositoryService;

    private MockWebServer mockWebServer;

    @Before
    public void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.repositoryService = new RepositoryService(WebClient.builder(), mockWebServer.url("/").toString());
    }

    @Test
    public void checkSonarURL_thenReturnFalse() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        );
        boolean exist = repositoryService.checkSonarURL("pvs-springboot");
        Assert.assertFalse(exist);
    }

    @Test
    public void checkSonarURL_thenReturnTrue() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        );
        boolean exist = repositoryService.checkSonarURL("http://140.124.181.143:9002/dashboard");
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(exist);
    }

    @Test
    public void checkGithubURL_thenReturnFalse() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        );
        boolean exist = repositoryService.checkGithubURL("pvs-springboot");
        Assert.assertFalse(exist);
    }

    @Test
    public void checkGithubURL_thenReturnTrue() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        );
        boolean exist = repositoryService.checkGithubURL("https://github.com/imper0502/pvs-spring-boot");
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(exist);
    }

}
