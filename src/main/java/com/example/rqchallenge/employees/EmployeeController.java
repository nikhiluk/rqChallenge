package com.example.rqchallenge.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController implements IEmployeeController{

    private static final String GET_ALL_EMPLOYEES = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String GET_BY_EMPLOYEE_ID = "https://dummy.restapiexample.com/api/v1/employee/";

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeController(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        URI targetUri = null;
        try {
            targetUri = new URI(GET_ALL_EMPLOYEES);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(HttpRequest.newBuilder().uri(targetUri).build(), HttpResponse.BodyHandlers.ofString());
            final ApiGetAllResponse apiGetAllResponse = objectMapper.readValue(httpResponse.body(), ApiGetAllResponse.class);
            return new ResponseEntity<>(apiGetAllResponse.getData(), HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return null;
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        URI targetUri = null;
        try {
            targetUri = new URI(GET_BY_EMPLOYEE_ID + id);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(HttpRequest.newBuilder().uri(targetUri).build(), HttpResponse.BodyHandlers.ofString());
            final ApiGetSingleResponse apiGetSingleResponse = objectMapper.readValue(httpResponse.body(), ApiGetSingleResponse.class);
            return new ResponseEntity<>(apiGetSingleResponse.getData(), HttpStatus.OK);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return null;
    }
}
