package com.example.rqchallenge.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private static final String GET_ALL_EMPLOYEES_URL = "http://localhost:9090/api/v1/employees";
    private static final String GET_EMPLOYEE_BY_ID_URL = "http://localhost:9090/api/v1/employee/";

    private RestTemplate restTemplate;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();

        employeeService = new EmployeeService(objectMapper, restTemplate, GET_ALL_EMPLOYEES_URL, GET_EMPLOYEE_BY_ID_URL);
    }

    @Test
    public void testUnableToParseResponse() {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>("Some String", HttpStatus.OK);
        when(restTemplate.getForEntity(GET_ALL_EMPLOYEES_URL, String.class)).thenReturn(responseEntity);

        final IOException ioException = assertThrows(IOException.class, () -> employeeService.getEmployees());

        assertEquals("Unable to parse response", ioException.getMessage());
    }


}