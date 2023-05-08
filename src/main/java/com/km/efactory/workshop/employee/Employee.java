package com.km.efactory.workshop.employee;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.km.efactory.workshop.security.role.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee implements UserDetails {
    @Id
    @SequenceGenerator(name = "employee_seq",sequenceName = "employee_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_seq")
    @Column(updatable = false)
    private Long id;
  
    @NotBlank(message = "Please enter valid company Id.")
    private String companyId;
 
    @NotBlank(message = "Please enter valid password.")
    @Size(min = 4,message = "Password is to short, min 4 characters.")
    private String password;

    @NotBlank(message = "Please enter valid name.")
    private String firstName;

    @NotBlank(message = "Please enter valid last name.")
    private String lastName;



    //@NotBlank(message = "Please enter valid employee role.")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.companyId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
