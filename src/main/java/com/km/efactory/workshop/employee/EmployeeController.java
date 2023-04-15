package com.km.efactory.workshop.employee;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.km.efactory.workshop.employee.exceptions.EmployeeIllegalStateException;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping(value = "/employees")
    public  ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<EmployeeRecord> getEmployeeById(@PathParam("id") Long id) {
        Employee employee = this.employeeService.getEmployeeById(id)
            .orElseThrow(() -> new EmployeeIllegalStateException("Employee by Id:"+id+" not found."));
        return ResponseEntity.ok(
            new EmployeeRecord(
                employee.getName(),
                employee.getCompanyId(),
                employee.getPosition())
        );
    }

    @PostMapping(value = "/employees")
    public ResponseEntity<EmployeeRecord> createEmployee(@Valid @RequestBody Employee employee) {
        Employee createdEmployee = this.employeeService.createEmployee(employee);

        return new ResponseEntity<>(new EmployeeRecord(createdEmployee),HttpStatus.CREATED); 
    }

    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathParam("id") Long id,@RequestBody Employee employee) {
        return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathParam("id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        String msg = "Employee by Id: "+ id + "deleted.";
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

   
}
