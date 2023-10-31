package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.exception.UnParseableInputException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class InputParser {

    public String parseNameToSearch(String searchString) {
        try {
            return URLDecoder.decode(searchString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
           throw new UnParseableInputException("search string invalid");
        }
    }
}
