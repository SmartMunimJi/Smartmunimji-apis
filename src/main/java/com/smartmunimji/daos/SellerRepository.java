package com.smartmunimji.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmunimji.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Seller findByEmail(String sellersemail);
}
