package com.example.rqchallenge.employees;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGetResponse {

    private String status;
    private List<Employee> data;


    public ApiGetResponse() {
    }

    public ApiGetResponse(String status, List<Employee> data) {
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