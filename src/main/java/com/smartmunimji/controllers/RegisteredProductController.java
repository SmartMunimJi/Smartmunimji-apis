package com.smartmunimji.controllers;

import com.smartmunimji.dtos.ProductRegistrationRequest;
import com.smartmunimji.services.RegisterProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sm/customer")
@CrossOrigin
public class RegisteredProductController {

	@Autowired
	private RegisterProductService registerProductService;

	@PostMapping("/register-product")
	public ResponseEntity<?> registerProduct(@RequestHeader("Authorization") String token,
			@RequestBody ProductRegistrationRequest request) {
		String message = registerProductService.registerProduct(token, request);
		return ResponseEntity.ok().body(message);
	}
}
