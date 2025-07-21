package com.smartmunimji.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/customer")
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
    	System.out.println("abc"+ credentials.getEmail()+" "+credentials.getPassword());
		Authentication auth = new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword());

		auth = authManager.authenticate(auth);

		String token = jwtUtil.createToken(auth);

		return ResponseEntity.ok(new LoginResponse("Login successful", token));
    }
}

