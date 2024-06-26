package com.eidiko.security.controller;

import com.eidiko.security.entity.Employee;
import com.eidiko.security.jwt.AuthRequest;
import com.eidiko.security.jwt.JwtUtils;
import com.eidiko.security.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/home")
    public String home() {
        return "<h1>This is Home Page</h1>";
    }

    @GetMapping("/user")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String user() {
        return "<h1>Welcome User</h1>";
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtils.generateToken(authRequest.getName());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
