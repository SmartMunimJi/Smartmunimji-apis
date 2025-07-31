package com.smartmunimji.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmunimji.entities.RegisteredProduct;

import java.util.Optional;
import java.util.List;

@Repository
public interface RegisteredProductDao extends JpaRepository<RegisteredProduct, Integer> {

	Optional<RegisteredProduct> findByOrderIdAndCustomerId(String orderId, Integer customerId);

	List<RegisteredProduct> findByCustomerId(Integer customerId);

	List<RegisteredProduct> findBySellerId(Integer sellerId);

//    List<RegisteredProduct> findByStatus(RegisteredProduct.Status status);

	boolean existsByOrderId(String orderId);
}
