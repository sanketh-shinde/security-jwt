package com.eidiko.security.service;

import com.eidiko.security.entity.Employee;
import com.eidiko.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByName(name);
        return employee.map(EmployeeDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Employee saveEmployee(Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        employee.setRoles(employee.getRoles());
        return employeeRepository.save(employee);
    }
}
