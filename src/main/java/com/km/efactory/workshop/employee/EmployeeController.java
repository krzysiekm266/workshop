package com.km.efactory.workshop.employee;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.km.efactory.workshop.employee.exceptions.EmployeeIllegalStateException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    public  ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees,HttpStatus.OK);
    }

    public ResponseEntity<Employee> getEmployeeById(Long id) {
        Employee employee = this.employeeService.getEmployeeById(id)
            .orElseThrow(() -> new EmployeeIllegalStateException("Employee by Id:"+id+" not found."));
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        //dopisac sprawdzenie empployee
        Field[] emlployeeFields = employee.getClass().getFields();
        Stream<Field> streamFields =  Stream.of(emlployeeFields).filter((field)-> field.getName().equals("id") );
        boolean employeeFiledsValueNotNull = streamFields.allMatch((field) -> {
            try {
                return field.get(field.getName()) != null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } );
        return  employeeFiledsValueNotNull ? new ResponseEntity<Employee>(employee,HttpStatus.OK) 
                : new ResponseEntity<String>("Employees properties illegal value.",HttpStatus.BAD_REQUEST) ;
    }

    public ResponseEntity<String> deleteEmployeeById(Long id) {
        this.employeeService.deleteEmployeeById(id);
        String msg = "Employee by Id: "+ id + "deleted.";
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
}
