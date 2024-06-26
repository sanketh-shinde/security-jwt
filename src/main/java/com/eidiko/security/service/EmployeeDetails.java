package com.eidiko.security.service;

import com.eidiko.security.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> roles;

    public EmployeeDetails(Employee employee) {
        this.name = employee.getName();
        this.password = employee.getPassword();
        this.roles = Arrays.stream(employee.getRoles().split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
