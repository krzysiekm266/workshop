package com.km.efactory.workshop;

import com.km.efactory.workshop.employee.Employee;
import com.km.efactory.workshop.employee.EmployeeRepository;
import com.km.efactory.workshop.security.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
	}


}
