package com.onlineShop.store.ProductFactory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onlineShop.store.Products.PC;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.repositories.ProductRepository;
@Component
public class PcFactory implements ProductFactory{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product createProduct() {
		return new PC();
	}

	 @Override
	    public void initializeAdditionalFields(Product product, Map<String, Object> additionalFields) {
	        ((PC) product).initializeFromAdditionalFields(additionalFields);
	    }

	@Override
	public Product getProductFromBdById(Long productId) {
		return (PC) productRepository.findById(productId).get();
	}
}