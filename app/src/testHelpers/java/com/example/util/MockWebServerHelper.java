package com.example.util;

import com.example.App;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MockWebServerHelper {

    public MockWebServer initMockWebServer() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        setBaseUrlToMockWebServer(mockWebServer);
        return mockWebServer;
    }

    /**
     * Change Retrofit's base url to the Mock Web Server provides us.
     */
    private void setBaseUrlToMockWebServer(MockWebServer mockWebServer) {
        App.getApplicationComponent().baseUrlInterceptor().setBaseUrl(mockWebServer.url("").toString());
    }

    public void enqueueErrorResponseForMockWebServer(MockWebServer mockWebServer, int restErrorCode) {
        mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 " + restErrorCode + " Boom!"));
    }

    public void enqueueJsonResponseFromFileForMockWebServer(MockWebServer mockWebServer, String fileName) throws IOException {
        mockWebServer.enqueue(new MockResponse().setBody(loadJson(fileName)));
    }

    @SuppressFBWarnings("OS_OPEN_STREAM_EXCEPTION_PATH")
    @SuppressWarnings("PMD.AssignmentInOperand")
    private String loadJson(String dummyJsonFileName) throws IOException {
        @Nullable InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dummyJsonFileName);
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        in.close();
        return builder.toString();
    }

    public void reset(MockWebServer mockWebServer) throws IOException {
        mockWebServer.shutdown();
        App.getApplicationComponent().baseUrlInterceptor().resetBaseUrl();
    }

}
