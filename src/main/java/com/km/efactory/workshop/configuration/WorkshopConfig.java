package com.km.efactory.workshop.configuration;

import com.km.efactory.workshop.employee.Employee;
import com.km.efactory.workshop.security.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.km.efactory.workshop.employee.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WorkshopConfig {
    private final EmployeeRepository employeeRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return companyId -> this.employeeRepository.findByCompanyId(companyId)
            .orElseThrow(() -> new UsernameNotFoundException("Employee not found."));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Employee admin = Employee.builder()
                    .firstName("John")
                    .lastName("Smith")
                    .companyId("A0001")
                    .role(Role.ADMIN)
                    .password(passwordEncoder().encode("123456"))
                    .build();
            this.employeeRepository.save(admin);

        };
    }

}
