package com.onlineShop.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineShop.store.entities.Product;
import com.onlineShop.store.entities.ProductRating;

public interface ProductRatingRepository extends JpaRepository <ProductRating,Long>{
	public List<ProductRating> findAllByProduct(Product product);
	public void deleteByProductId(Long productid);
	
}
