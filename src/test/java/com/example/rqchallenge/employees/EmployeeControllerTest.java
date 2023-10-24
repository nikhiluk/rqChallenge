package com.example.rqchallenge.employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllEmployees() {
        ResponseEntity<List<Employee>> response = restTemplate.exchange("http://localhost:" + port,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final List<Employee> body = response.getBody();
        assertNotNull(body);
        assertEquals(24, body.size());
    }

    @Test
    public void getEmployeeById() {
        ResponseEntity<Employee> response = restTemplate.exchange("http://localhost:" + port + "/1",
                HttpMethod.GET,
                null,
                Employee.class);

        final int statusCodeValue = response.getStatusCodeValue();

        assertEquals(200, statusCodeValue);
        final Employee employee = response.getBody();
        assertNotNull(employee);
        assertEquals(1, employee.getId());
        assertEquals("Tiger Nixon", employee.getEmployeeName());
    }

}