package com.km.efactory.workshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.km.efactory.workshop.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorizeRequest -> 
            authorizeRequest
                .requestMatchers("/api/v1/logout","/api/v1/authenticated").permitAll().anyRequest().authenticated()
        )
        .sessionManagement(sessionManagement -> 
            sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> 
            logout
                .logoutUrl("/api/v1/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request,response,authentication) -> SecurityContextHolder.clearContext())
        )
        .build();
        
        //.csrf().disable()
        // .authorizeHttpRequests()
        // .requestMatchers("/api/v1/**")
        //     .permitAll()
        // .anyRequest()
        //     .authenticated()
        // .and()
        //     .sessionManagement()
        //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        //     .authenticationProvider(authenticationProvider)
        //     .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
        // .logout()
        //     .logoutUrl("/api/v1/logout")
        //     .addLogoutHandler(logoutHandler)
        //     .logoutSuccessHandler((request,response,authentication) -> SecurityContextHolder.clearContext())
        // .and()
        // .build();

    
  }  
}
