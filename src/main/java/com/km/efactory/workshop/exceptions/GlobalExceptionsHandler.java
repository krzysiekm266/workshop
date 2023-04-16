package com.km.efactory.workshop.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(value={ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstrainViolationException(ConstraintViolationException ex) {
       List<String> violationList = ex.getConstraintViolations()
        .stream()
        .map(v -> v.getMessage())
        .collect(Collectors.toList());
      
        
        return new ResponseEntity<>(violationList,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> violationList = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(v -> v.getDefaultMessage())
            .collect(Collectors.toList());
        
         return new ResponseEntity<>(violationList,HttpStatus.BAD_REQUEST);
     }
}
