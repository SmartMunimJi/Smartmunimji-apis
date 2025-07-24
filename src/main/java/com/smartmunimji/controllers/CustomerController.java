package com.smartmunimji.controllers;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmunimji.dto.Credentials;
import com.smartmunimji.dto.LoginResponse;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.services.CustomerService;
import com.smartmunimji.util.JwtUtil;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired 
    private CustomerService customerService;
    
    @Autowired 
    private JwtUtil jwtUtil;
    
    @Autowired
	private AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        Customer saved = customerService.registerCustomer(customer);
        return ResponseEntity.ok("Customer registered with ID: " + saved.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );
            String token = jwtUtil.createToken(authentication);
            return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}
