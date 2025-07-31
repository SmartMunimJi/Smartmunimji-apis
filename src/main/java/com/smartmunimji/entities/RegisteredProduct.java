package com.smartmunimji.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "registered_products")
public class RegisteredProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller seller;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "purchase_date")
	private LocalDate purchaseDate;

	@Column(name = "warranty_expiry_date")
	private LocalDate warrantyExpiryDate;

	@Column(name = "registration_date", nullable = false)
	private LocalDateTime registrationDate = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	private RegistrationStatus status = RegistrationStatus.active;

	@Lob
	private byte[] productImage;

	public enum RegistrationStatus {
		active, expired, claimed
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public LocalDate getWarrantyExpiryDate() {
		return warrantyExpiryDate;
	}

	public void setWarrantyExpiryDate(LocalDate warrantyExpiryDate) {
		this.warrantyExpiryDate = warrantyExpiryDate;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public RegistrationStatus getStatus() {
		return status;
	}

	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}
}
