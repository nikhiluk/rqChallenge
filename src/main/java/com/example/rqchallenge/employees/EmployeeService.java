package com.example.rqchallenge.employees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final String getAllEmployeesUrl;
    private final String getEmployeesByIdUrl;

    @Autowired
    public EmployeeService(ObjectMapper objectMapper, RestTemplate restTemplate,
                           @Value("${getAllEmployeesUrl}") String getAllEmployeesUrl,
                           @Value("${getEmployeesByIdUrl}") String getEmployeesByIdUrl) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.getAllEmployeesUrl = getAllEmployeesUrl;
        this.getEmployeesByIdUrl = getEmployeesByIdUrl;
    }

    public List<Employee> getEmployees() throws IOException {
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(getAllEmployeesUrl, String.class);
        final ApiGetAllResponse body = parseResponse(responseEntity.getBody(), ApiGetAllResponse.class);
        return body.getData();
    }

    public List<Employee> getEmployeesByNameSearch(String nameToSearch) throws IOException {
        final List<Employee> employeeList = getEmployees();
        return employeeList.stream().filter(r -> r.getEmployeeName().contains(nameToSearch)).collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) throws IOException{
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(getEmployeesByIdUrl + id, String.class);
        final ApiGetSingleResponse body = parseResponse(responseEntity.getBody(), ApiGetSingleResponse.class);
        return body.getData();

    }

    public <T> T parseResponse(String json, Class<T> valueType) throws IOException {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new IOException("Unable to parse response");
        }
    }

    public int getHighestSalary() throws IOException {
        final List<Employee> employeeList = getEmployees();
        return getEmployeesSortedBySalary(employeeList)
                .get(employeeList.size() - 1)
                .getEmployeeSalary();
    }

    private List<Employee> getEmployeesSortedBySalary(List<Employee> employeeList) {
        return employeeList.stream().sorted(Comparator.comparing(Employee::getEmployeeSalary))
                .collect(Collectors.toList());
    }

    public List<String> getNameOfTopTenHighestEarners() throws IOException {
        final List<Employee> employeeList = getEmployees();

        List<Employee> employees = getEmployeesSortedBySalary(employeeList);
        int size = employees.size();
        final List<String> toReturn = new ArrayList<>();
        int i = 0;
        while (i < 10) {
            toReturn.add(employees.get(--size).getEmployeeName());
            i++;
        }

        return toReturn;
    }
}
