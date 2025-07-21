package com.smartmunimji.services;

import com.smartmunimji.daos.SellerRepository;
import com.smartmunimji.entities.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class SellerDetailsService implements UserDetailsService {

	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Seller seller = sellerRepository.findByEmail(email);
		if (seller == null) {
			throw new UsernameNotFoundException("Seller not found with email: " + email);
		}

		return seller;
	}
}