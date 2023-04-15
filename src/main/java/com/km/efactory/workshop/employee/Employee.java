package com.km.efactory.workshop.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
  
    @NotBlank(message = "Please enter valid company Id.")
    private String companyId;
 
    @NotBlank(message = "Please enter valid company position.")
    private String position;

    @NotBlank(message = "Please enter valid employee name.")
    private String name;
}
