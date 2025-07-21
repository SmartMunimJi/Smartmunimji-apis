package com.smartmunimji.services;

import com.smartmunimji.daos.CustomerRepository;
import com.smartmunimji.entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new UsernameNotFoundException("Customer not found with email: " + email);
		}
		return customer;
	}
}
