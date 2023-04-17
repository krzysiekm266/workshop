package com.km.efactory.workshop.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    
    public Optional<Employee> findByCompanyId(String companyId);
}
