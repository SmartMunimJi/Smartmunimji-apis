package com.smartmunimji.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartmunimji.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByEmail(String email);
}
