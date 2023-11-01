package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.controller.ApiGetAllResponse;
import com.example.rqchallenge.employees.controller.ApiGetSingleResponse;
import com.example.rqchallenge.employees.exception.UnParseableResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final String getAllEmployeesUrl;
    private final String getEmployeesByIdUrl;
    private final String createEmployeeUrl;
    private final String deleteEmployeeUrl;
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(ObjectMapper objectMapper, RestTemplate restTemplate,
                           @Value("${getAllEmployeesUrl}") String getAllEmployeesUrl,
                           @Value("${getEmployeesByIdUrl}") String getEmployeesByIdUrl,
                           @Value("${createEmployeeUrl}") String createEmployeeUrl,
                           @Value("${deleteEmployeeUrl}") String deleteEmployeeUrl) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.getAllEmployeesUrl = getAllEmployeesUrl;
        this.getEmployeesByIdUrl = getEmployeesByIdUrl;
        this.createEmployeeUrl = createEmployeeUrl;
        this.deleteEmployeeUrl = deleteEmployeeUrl;
    }

    public List<Employee> getEmployees() {
        logger.info("Starting call to get employee");
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(getAllEmployeesUrl, String.class);
        final ApiGetAllResponse body = parseResponse(responseEntity.getBody(), ApiGetAllResponse.class);
        return body.getData();
    }

    public List<Employee> getEmployeesByNameSearch(String nameToSearch) {
        logger.info("Starting call to get employee by name");
        final List<Employee> employeeList = getEmployees();
        return employeeList.stream().filter(r -> r.getEmployeeName().contains(nameToSearch)).collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        logger.info("Starting call to get employee by id");
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(getEmployeesByIdUrl + id, String.class);
        final ApiGetSingleResponse body = parseResponse(responseEntity.getBody(), ApiGetSingleResponse.class);
        return body.getData();

    }

    public <T> T parseResponse(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse response");
            throw new UnParseableResponseException("Unable to parse response");
        }
    }

    public int getHighestSalary() {
        logger.info("Starting call to get highest salary");
        final List<Employee> employeeList = getEmployees();
        return getEmployeesSortedBySalary(employeeList)
                .get(employeeList.size() - 1)
                .getEmployeeSalary();
    }

    private List<Employee> getEmployeesSortedBySalary(List<Employee> employeeList) {
        return employeeList.stream().sorted(Comparator.comparing(Employee::getEmployeeSalary))
                .collect(Collectors.toList());
    }

    public List<String> getNamesOfTopTenHighestEarners() {
        logger.info("Starting call to get top earners");

        final List<Employee> employeeList = getEmployees();
        List<Employee> employees = getEmployeesSortedBySalary(employeeList);

        Collections.reverse(employees);

        return employees.subList(0, 10).stream().map(Employee::getEmployeeName).collect(Collectors.toList());
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        logger.info("Starting call to create employee");

        final ResponseEntity<String> postForEntity = restTemplate.postForEntity(createEmployeeUrl, employeeInput, String.class);
        final ApiGetSingleResponse body = parseResponse(postForEntity.getBody(), ApiGetSingleResponse.class);
        return body.getData();
    }

    public void deleteEmployeeById(String id) {
        logger.info("Starting call to delete employee");
        restTemplate.delete(deleteEmployeeUrl + id);
    }

    //TODO: Ideally, the usage of rest template can be further pushed down a layer into a rest client to handle circuit breaking etc.
}
