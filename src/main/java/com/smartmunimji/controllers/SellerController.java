package com.smartmunimji.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.smartmunimji.dto.Credentials;
import com.smartmunimji.dto.LoginResponse;
import com.smartmunimji.entities.Seller;
import com.smartmunimji.services.SellerService;
import com.smartmunimji.util.JwtUtil;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials credentials) {
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

		String token = jwtUtil.createToken(auth);
		return ResponseEntity.ok(new LoginResponse("Login successful", token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Seller seller) {
		Seller saved = sellerService.registerSeller(seller);
		return ResponseEntity.ok("Seller registered with ID: " + saved.getId());
	}
}
