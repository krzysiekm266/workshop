package com.km.efactory.workshop.employee;


public record EmployeeRecordDto(String companyId, String firstName, String lastName) {
    public EmployeeRecordDto(Employee employee) {
        this(
                employee.getCompanyId(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }
}
