package com.example.rqchallenge.employees;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    private int id;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_salary")
    private long employeeSalary;
    @JsonProperty("employee_age")
    private int employeeAge;
    @JsonProperty("profile_image")
    private int profileImage;

    public Employee() {
    }

    public Employee(int id, String employeeName, long employeeSalary, int employeeAge, int profileImage) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeAge = employeeAge;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public long getEmployeeSalary() {
        return employeeSalary;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
