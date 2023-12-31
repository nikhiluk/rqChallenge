package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.exception.UnParseableResponseException;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private static final String GET_ALL_EMPLOYEES_URL = "http://localhost:9090/api/v1/employees";
    private static final String GET_EMPLOYEE_BY_ID_URL = "http://localhost:9090/api/v1/employee/";
    private static final String CREATE_EMPLOYEE_URL = "http://localhost:9090/api/v1/create";
    private static final String DELETE_EMPLOYEE_URL = "http://localhost:9090/api/v1/delete/";

    private RestTemplate restTemplate;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();

        employeeService = new EmployeeService(objectMapper, restTemplate,
                GET_ALL_EMPLOYEES_URL,
                GET_EMPLOYEE_BY_ID_URL,
                CREATE_EMPLOYEE_URL,
                DELETE_EMPLOYEE_URL);
    }

    @Test
    public void testUnableToParseResponse() {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>("Some String", HttpStatus.OK);
        when(restTemplate.getForEntity(GET_ALL_EMPLOYEES_URL, String.class)).thenReturn(responseEntity);

        final UnParseableResponseException exception = assertThrows(UnParseableResponseException.class, () -> employeeService.getEmployees());

        assertEquals("Unable to parse response", exception.getMessage());
    }

    //TODO: Cover more error scenarios to mimic backend failures.



}