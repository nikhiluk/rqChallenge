package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.service.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGetSingleResponse {

    private String status;
    private Employee data;

    public ApiGetSingleResponse() {
    }

    public ApiGetSingleResponse(String status, Employee data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public Employee getData() {
        return data;
    }
}
