package com.example.rqchallenge.employees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EmployeeController implements IEmployeeController {

    private static final String CREATE_EMPLOYEE_URL = "http://localhost:9090/api/v1/create";

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(ObjectMapper objectMapper, RestTemplate restTemplate, EmployeeService employeeService) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        final List<Employee> employeeList = employeeService.getEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws IOException {
        String nameToSearch = URLDecoder.decode(searchString, StandardCharsets.UTF_8.toString());
        List<Employee> employees = employeeService.getEmployeesByNameSearch(nameToSearch);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) throws IOException {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws IOException {
        final int highestSalary = employeeService.getHighestSalary();
        return new ResponseEntity<>(highestSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws IOException {
        final List<String> toReturn = employeeService.getNameOfTopTenHighestEarners();
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {

        final ResponseEntity<String> postForEntity = restTemplate.postForEntity(CREATE_EMPLOYEE_URL, employeeInput, String.class);

        try {
            final ApiGetSingleResponse apiGetSingleResponse = objectMapper.readValue(postForEntity.getBody(), ApiGetSingleResponse.class);
            return new ResponseEntity<>(apiGetSingleResponse.getData(), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        restTemplate.delete("http://localhost:9090/api/v1/delete/" + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
