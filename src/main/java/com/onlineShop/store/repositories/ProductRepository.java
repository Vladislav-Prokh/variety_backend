package com.onlineShop.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineShop.store.entities.Product;

public interface ProductRepository extends JpaRepository <Product,Long>{
	public void deleteProductByProductName(String product_name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByProductNameContainingIgnoreCase(@Param("name") String name);
	 
	 @Query("SELECT DISTINCT UPPER(p.brand) FROM Product p")
	 List<String> findDistinctBrandsIgnoreCase();
	 
}
