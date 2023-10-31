package com.example.rqchallenge.employees;

import org.springframework.http.HttpStatus;

class ApiError {

   private HttpStatus status;
   private String message;
   private String debugMessage;


   ApiError(HttpStatus status, Throwable ex) {
       this.status = status;
       this.message = "Unexpected error";
   }
}