package com.onlineShop.store.ProductFactory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineShop.store.Products.Shoe;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.repositories.ProductRepository;

public class ShoeFactory implements ProductFactory {
	
	@Autowired
	private ProductRepository productRepository;
    @Override
    public Product createProduct() {
        return new Shoe();
    }
    
    @Override
    public void initializeAdditionalFields(Product product, Map<String, Object> additionalFields) {
        ((Shoe) product).initializeFromAdditionalFields(additionalFields);
    }

	@Override
	public Product getProductFromBdById(Long productId) {
		return (Shoe) productRepository.findById(productId).get();
	}
}