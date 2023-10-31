package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.service.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGetAllResponse {

    private String status;
    private List<Employee> data;


    public ApiGetAllResponse() {
    }

    public ApiGetAllResponse(String status, List<Employee> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public List<Employee> getData() {
        return data;
    }
}
