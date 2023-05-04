package com.km.efactory.workshop.security.auth;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.km.efactory.workshop.employee.exceptions.EmployeeNotExistException;
import com.km.efactory.workshop.security.token.Token;
import com.km.efactory.workshop.security.token.TokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.km.efactory.workshop.employee.Employee;
import com.km.efactory.workshop.employee.EmployeeRepository;
import com.km.efactory.workshop.security.jwt.JwtService;
import com.km.efactory.workshop.security.role.Role;
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
        Employee employee = Employee.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .companyId(request.getCompanyId())
            .password(request.getPassword())
            .role(Role.USER)
            .build();
        Employee savedEmployee = this.employeeRepository.save(employee);
        String jwtToken = this.jwtService.generateToken(employee);
        String  refreshToken = this.jwtService.generateRefreshToken(employee);
        saveEmployeeToken(savedEmployee,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCompanyId(),request.getPassword())
        );
        Employee employee = employeeRepository.findByCompanyId(request.getCompanyId()).orElseThrow();
        String  jwtToken = jwtService.generateToken(employee);
        String refreshToken = jwtService.generateRefreshToken(employee);
        revokeAllEmployeeTokens(employee);
        saveEmployeeToken(employee,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    private void saveEmployeeToken(Employee  employee,String jwtToken) {
        Token token = Token.builder()
                .employee(employee)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        this.tokenRepository.save(token);

    }
    private void revokeAllEmployeeTokens(Employee employee) {
       List<Token> validEmployeeTokens = this.tokenRepository.findAllValidTokenByEmployee(employee.getId());
       if(validEmployeeTokens.isEmpty())
           return;

       validEmployeeTokens.forEach( token -> {
           token.setExpired(true);
           token.setRevoked(true);
       });
       this.tokenRepository.saveAll(validEmployeeTokens);


    }
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String employeeCompanyId;
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        refreshToken = authHeader.substring(7);
        employeeCompanyId = this.jwtService.extractEmployeeCompanyId(refreshToken);
        if (employeeCompanyId != null) {
            Employee employee = this.employeeRepository.findByCompanyId(employeeCompanyId).orElseThrow(
                    () -> new EmployeeNotExistException("Employee company id: "+employeeCompanyId+" not exist.")
            );
            if (this.jwtService.isTokenValid(refreshToken, employee)) {
                String accessToken = this.jwtService.generateToken(employee);
                revokeAllEmployeeTokens(employee);
                saveEmployeeToken(employee, accessToken);
                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }
}
