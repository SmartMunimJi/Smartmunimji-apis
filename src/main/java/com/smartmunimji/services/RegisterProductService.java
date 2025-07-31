package com.smartmunimji.services;

import com.smartmunimji.daos.*;
import com.smartmunimji.dtos.ProductRegistrationRequest;
import com.smartmunimji.entities.*;
import com.smartmunimji.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterProductService {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private SellerDao sellerDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private RegisteredProductDao registeredProductDao;

	public String registerProduct(String token, ProductRegistrationRequest request) {
		token = token.substring(7); // Remove "Bearer "
		int customerId = jwtUtil.extractUserId(token);

		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Product product = productDao.findById(request.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

		Seller seller = sellerDao.findBySellername(request.getSellerName())
				.orElseThrow(() -> new RuntimeException("Seller not found with name: " + request.getSellerName()));

		RegisteredProduct rp = new RegisteredProduct();
		rp.setCustomer(customer);
		rp.setProduct(product);
		rp.setSeller(product.getSeller());
		rp.setOrderId(request.getOrderId());
		rp.setPurchaseDate(request.getPurchaseDate());
		rp.setRegistrationDate(LocalDateTime.now());
		rp.setStatus(RegisteredProduct.RegistrationStatus.active);
		rp.setWarrantyExpiryDate(request.getPurchaseDate().plusMonths(product.getWarrantyPeriod()));

		registeredProductDao.save(rp);

		return "Product registered successfully";
	}
}
