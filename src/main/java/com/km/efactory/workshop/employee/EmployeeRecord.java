package com.km.efactory.workshop.employee;


public record EmployeeRecord(String firstName,String lastName) {
    public EmployeeRecord(Employee employee) {
        this(
            employee.getFirstName(),
            employee.getLastName()
        );
    }
}
