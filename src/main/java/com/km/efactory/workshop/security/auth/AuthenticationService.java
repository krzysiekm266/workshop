package com.km.efactory.workshop.security.auth;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.km.efactory.workshop.employee.Employee;
import com.km.efactory.workshop.employee.EmployeeRepository;
import com.km.efactory.workshop.security.jwt.JwtService;
import com.km.efactory.workshop.security.token.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        throw new RuntimeException("Not implemented.");
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        throw new RuntimeException("Not implemented");
    }

    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        throw new RuntimeException("Not implemented");

    }
    private void saveEmployeeToken(Employee  employee,String jwtToken) {
        throw new RuntimeException("Not implemented");

    }
    private void revokeAllEmployeeTokens(Employee employee) {
        throw new RuntimeException("Not implemented");

    }
}
