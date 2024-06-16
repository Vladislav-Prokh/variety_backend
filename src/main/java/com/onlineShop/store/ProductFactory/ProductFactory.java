package com.onlineShop.store.ProductFactory;

import java.util.Map;


import com.onlineShop.store.entities.Product;

public interface ProductFactory {
	
	Product createProduct();
	void initializeAdditionalFields(Product product, Map<String, Object> additionalFields);
	Product getProductFromBdById(Long productId);
}
