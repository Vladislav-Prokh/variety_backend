package com.onlineShop.store.ProductFactory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onlineShop.store.Products.Accessory;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.repositories.ProductRepository;


@Component
public class AccessoryFactory implements ProductFactory{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product createProduct() {
		return new Accessory();
	}

	@Override
	public void initializeAdditionalFields(Product product, Map<String, Object> additionalFields) {
        ((Accessory) product).initializeFromAdditionalFields(additionalFields);
		
	}

	@Override
	public Product getProductFromBdById(Long productId) {
		return (Accessory) productRepository.findById(productId).get();
	}

}