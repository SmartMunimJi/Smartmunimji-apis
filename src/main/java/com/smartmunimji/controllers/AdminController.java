package com.smartmunimji.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.smartmunimji.dto.Credentials;
import com.smartmunimji.dto.LoginResponse;
import com.smartmunimji.entities.Admin;
import com.smartmunimji.services.AdminService;
import com.smartmunimji.util.JwtUtil;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials credentials) {

		System.out.println("abc"+credentials.getEmail()+" "+credentials.getPassword());
		Authentication auth = new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword());


		auth = authManager.authenticate(auth);

		String token = jwtUtil.createToken(auth);

		return ResponseEntity.ok(new LoginResponse("Login successful", token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Admin admin) {
		Admin saved = adminService.registerAdmin(admin);
		return ResponseEntity.ok("Admin registered with ID: " + saved.getId());
	}
}