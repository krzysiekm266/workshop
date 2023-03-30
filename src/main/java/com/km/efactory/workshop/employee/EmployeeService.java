package com.km.efactory.workshop.employee;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        List<String> checkEmployeeStatus = checkEmployeeBeforeCreate(employee);
        if(!checkEmployeeStatus.get(0).equals("OK")) {
            String msg = String.join(",", checkEmployeeStatus);
            throw new EmployeeIllegalStateException(msg);
        } 
        return this.employeeRepository.save(employee);
    }
    /**check employee attributes for null values */
    private List<String> checkEmployeeBeforeCreate(Employee employee) {
        List<String> employeeIllegalValueName = new ArrayList<>();
        
        Field[] emlployeeFields = employee.getClass().getFields();
        Stream<Field> streamFields =  Stream.of(emlployeeFields).filter((field)-> !field.getName().equals("id") );
        boolean employeeFiledsValuesNotNull = streamFields.allMatch((field) -> {
            try {
                if(field.get(field.getName()).equals(null)) {
                    employeeIllegalValueName.add(field.getName());
                    return false;
                }
                return true;
            } catch (IllegalArgumentException e) {
                //e.printStackTrace();
                employeeIllegalValueName.add(field.getName());
                return false;
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
                employeeIllegalValueName.add(field.getName());
                return false;
            }
        } );
        if(employeeFiledsValuesNotNull) employeeIllegalValueName.add("OK");
        return employeeIllegalValueName;
    }
    /*************************************************** */

    public Employee updateEmployeeById(Long id,Employee employee) {
         Employee employeeToUpdate = this.employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeIllegalStateException("Update failed. Employee by Id:"+id+"not exist."));
            udpdateEmployee(employeeToUpdate,employee);
         return null;
    }
    /**check employee before update */
    private boolean udpdateEmployee(Employee employeeToUpdate,Employee newEmployeValues) {
        throw new RuntimeException("Not implemented yet.");
        
    }
    /******************************* */
    public void deleteEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotExistException("Delete failed. Employee by Id:"+id+"not exist."));
        this.employeeRepository.delete(employee);    
    }
}
