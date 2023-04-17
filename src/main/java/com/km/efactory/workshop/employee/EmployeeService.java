package com.km.efactory.workshop.employee;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.km.efactory.workshop.employee.exceptions.EmployeeIllegalStateException;
import com.km.efactory.workshop.employee.exceptions.EmployeeNotExistException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return this.employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) throws EmployeeIllegalStateException {
        
        return this.employeeRepository.save(employee);
    }
    
    

    public Employee updateEmployeeById(Long id,Employee employee) {
         Employee employeeToUpdate = this.employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeIllegalStateException("Update failed. Employee by Id:"+id+"not exist."));
        //w budowie
        throw new RuntimeException("Not implemented .");
        
    }
   
    public String deleteEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotExistException("Delete failed. Employee by Id:"+id+"not exist."));
        this.employeeRepository.delete(employee); 
        return "Employee by Id: "+id+" deleted.";   
    }
}
