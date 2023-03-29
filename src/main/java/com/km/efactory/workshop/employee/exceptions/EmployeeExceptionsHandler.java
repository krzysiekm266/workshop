package com.km.efactory.workshop.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.km.efactory.workshop.employee.EmployeeController;

@ControllerAdvice(basePackageClasses = {EmployeeController.class})
public class EmployeeExceptionsHandler {

    @ExceptionHandler(value = {EmployeeIllegalStateException.class})
    public ResponseEntity<Object> handleEmployeeIllegalStateException(EmployeeIllegalStateException ex) {
        return new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value ={EmployeeNotExistException.class})
    public ResponseEntity<Object> handleEmployeeNotExistException(EmployeeNotExistException ex) {
        return new ResponseEntity<>(ex,HttpStatus.NOT_FOUND);
    }
}
