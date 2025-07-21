package com.smartmunimji.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "registered_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;
    private Integer sellerId;
    private Integer productId;

    private String orderId;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiryDate;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('active','expired','claimed') DEFAULT 'active'")
    private Status status = Status.active;

    @Lob
    private byte[] productImage;

    public enum Status {
        active, expired, claimed
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public java.sql.Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(java.sql.Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}
}

