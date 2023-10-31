package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.service.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;
    private final InputParser inputParser;

    @Autowired
    public EmployeeController(EmployeeService employeeService, InputParser inputParser) {
        this.employeeService = employeeService;
        this.inputParser = inputParser;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        final List<Employee> employeeList = employeeService.getEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        String nameToSearch = inputParser.parseNameToSearch(searchString);
        List<Employee> employees = employeeService.getEmployeesByNameSearch(nameToSearch);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        final int highestSalary = employeeService.getHighestSalary();
        return new ResponseEntity<>(highestSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        final List<String> namesOfTopTenHighestEarners = employeeService.getNamesOfTopTenHighestEarners();
        return new ResponseEntity<>(namesOfTopTenHighestEarners, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        Employee employee = employeeService.createEmployee(employeeInput);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
