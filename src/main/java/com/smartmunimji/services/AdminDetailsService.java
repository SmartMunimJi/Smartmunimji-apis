package com.smartmunimji.services;

import com.smartmunimji.daos.AdminRepository;
import com.smartmunimji.entities.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByEmail(email);
		if (admin == null) {
			throw new UsernameNotFoundException("Admin not found with email: " + email);
		}

		return admin;
	}
}