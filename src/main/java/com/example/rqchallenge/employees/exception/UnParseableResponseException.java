package com.example.rqchallenge.employees.exception;

public class UnParseableResponseException extends RuntimeException {
    public UnParseableResponseException(String message) {
        super(message);
    }
}
