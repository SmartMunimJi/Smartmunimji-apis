package com.smartmunimji.services;

import java.util.List;
import com.smartmunimji.entities.Product;

public interface ProductService {
	Product createProduct(Product product);

	List<Product> getAllProducts();

	List<Product> getProductsBySellerId(int sellerId);

	Product updateProduct(int id, Product updatedProduct);

	void deleteProduct(int id);
}
