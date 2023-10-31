package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.exception.ApiError;
import com.example.rqchallenge.employees.service.Employee;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9090)
public class EmployeeControllerUnhappyPathTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        stubFor(get(urlEqualTo("/api/v1/employees"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("unparseable response")));
    }

    @Test
    public void testUnparseableResponse() {
        ResponseEntity<ApiError> response = restTemplate.exchange("http://localhost:" + port,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(422, statusCodeValue);
        final String body = response.getBody().getMessage();
        assertNotNull(body);
        assertEquals("Unable to parse response", body);
    }

    @Test
    public void testUnparseableInput() {
        ResponseEntity<ApiError> response = restTemplate.exchange("http://localhost:" + port + "/search/%",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(400, statusCodeValue);
        final String body = response.getBody().getMessage();
        assertNotNull(body);
        assertEquals("search string invalid", body);
    }
}
