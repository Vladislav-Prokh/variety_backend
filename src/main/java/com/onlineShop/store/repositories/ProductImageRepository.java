package com.onlineShop.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineShop.store.entities.ProductImage;

public interface ProductImageRepository  extends JpaRepository<ProductImage,Long> {
	public void deleteByProductId(Long productId); 

}
