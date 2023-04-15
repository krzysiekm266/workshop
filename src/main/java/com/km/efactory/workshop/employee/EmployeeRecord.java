package com.km.efactory.workshop.employee;


public record EmployeeRecord(String name,String companyId, String position ) {
    public EmployeeRecord(Employee employee) {
        this(
            employee.getName(),
            employee.getCompanyId(),
            employee.getPosition());
    }
}
