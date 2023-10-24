package com.example.rqchallenge.employees;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

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
