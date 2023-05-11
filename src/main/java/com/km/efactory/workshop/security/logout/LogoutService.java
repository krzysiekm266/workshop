package com.km.efactory.workshop.security.logout;

import com.km.efactory.workshop.security.token.Token;
import com.km.efactory.workshop.security.token.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
       final String authHeader = request.getHeader("Authorization");
       final String jwt;
       if(authHeader == null || !authHeader.startsWith("Bearer ")) {
           return;
       }
       jwt = authHeader.substring(7);
       Token storedToken = this.tokenRepository.findByToken(jwt).orElse(null);
       if(storedToken != null) {
           storedToken.setExpired(true);
           storedToken.setRevoked(true);
           this.tokenRepository.save(storedToken);
           SecurityContextHolder.clearContext();
       }
        
    }
    
}
