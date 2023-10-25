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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class EmployeeController implements IEmployeeController {

    //    private static final String GET_ALL_EMPLOYEES = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String GET_ALL_EMPLOYEES = "http://localhost:9090/api/v1/employees";
    private static final String GET_BY_EMPLOYEE_ID = "http://localhost:9090/api/v1/employee/";

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Autowired
    public EmployeeController(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {

        final List<Employee> employeeList = getEmployees();

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        try {
            String nameToSearch = URLDecoder.decode(searchString, StandardCharsets.UTF_8.toString());
            final List<Employee> employeeList = getEmployees();
            final List<Employee> employeesMatching = employeeList.stream().filter(r -> r.getEmployeeName().contains(nameToSearch)).collect(Collectors.toList());
            return new ResponseEntity<>(employeesMatching, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Employee> getEmployees() throws com.fasterxml.jackson.core.JsonProcessingException {
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(GET_ALL_EMPLOYEES, String.class);
        final ApiGetAllResponse body = objectMapper.readValue(responseEntity.getBody(), ApiGetAllResponse.class);
        return body.getData();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        try {
            final ResponseEntity<String> responseEntity = restTemplate.getForEntity(GET_BY_EMPLOYEE_ID + id, String.class);
            final ApiGetSingleResponse body = objectMapper.readValue(responseEntity.getBody(), ApiGetSingleResponse.class);
            return new ResponseEntity<>(body.getData(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        try {
            final List<Employee> employeeList = getEmployees();
            final int highestSalary = getEmployeesSortedBySalary(employeeList)
                    .get(employeeList.size() - 1)
                    .getEmployeeSalary();

            return new ResponseEntity<>(highestSalary, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        try {
            final List<String> toReturn = new ArrayList<>();

            final List<Employee> employeeList = getEmployees();

            List<Employee> employees = getEmployeesSortedBySalary(employeeList);
            int size = employees.size();
            for (int i = 0; i < 10; i++) {
                toReturn.add(employees.get(--size).getEmployeeName());
            }
            return new ResponseEntity<>(toReturn, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Employee> getEmployeesSortedBySalary(List<Employee> employeeList) {
        return employeeList.stream().sorted(Comparator.comparing(Employee::getEmployeeSalary))
                .collect(Collectors.toList());
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
