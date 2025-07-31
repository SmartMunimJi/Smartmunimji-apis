package com.smartmunimji.controllers;

import com.smartmunimji.dtos.WarrantyClaimRequest;
import com.smartmunimji.services.WarrantyClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
public class WarrantyClaimController {

	@Autowired
	private WarrantyClaimService warrantyClaimService;

	@PostMapping("/customer/warranty-claim")
	public ResponseEntity<?> submitClaim(@RequestHeader("Authorization") String token,
			@RequestBody WarrantyClaimRequest request) {
		String message = warrantyClaimService.submitClaim(token, request);
		return ResponseEntity.ok().body(message);
	}
}
