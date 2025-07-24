package com.smartmunimji.daos;

import com.smartmunimji.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SellerDao extends JpaRepository<Seller, Integer> {
    Optional<Seller> findBySellersemail(String sellersemail);
}
