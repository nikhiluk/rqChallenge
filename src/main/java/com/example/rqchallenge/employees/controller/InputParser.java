package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.exception.UnParseableInputException;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class InputParser {
    Logger logger = LoggerFactory.getLogger(InputParser.class);

    public String parseNameToSearch(String searchString) {
        try {
            return URLDecoder.decode(searchString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException|IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new UnParseableInputException("search string invalid");
        }
    }
}
