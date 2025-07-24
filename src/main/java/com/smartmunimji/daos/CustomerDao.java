package com.smartmunimji.daos;

import com.smartmunimji.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}
