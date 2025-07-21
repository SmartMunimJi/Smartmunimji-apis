package com.smartmunimji.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartmunimji.daos.SellerRepository;
import com.smartmunimji.entities.Seller;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Seller registerSeller(Seller seller) {
		seller.setPassword(passwordEncoder.encode(seller.getPassword()));
		return sellerRepository.save(seller);
	}

	public Seller getByEmail(String email) {
		return sellerRepository.findByEmail(email);
	}
}
