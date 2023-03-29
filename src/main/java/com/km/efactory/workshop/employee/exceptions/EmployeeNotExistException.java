package com.km.efactory.workshop.employee.exceptions;

public class EmployeeNotExistException extends IllegalStateException {

    public EmployeeNotExistException() {
    }

    public EmployeeNotExistException(String msg) {
        super(msg);
    }
    
}
