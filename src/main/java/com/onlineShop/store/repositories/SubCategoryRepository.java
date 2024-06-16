package com.onlineShop.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineShop.store.entities.Subcategory;

public interface SubCategoryRepository  extends JpaRepository <Subcategory,Long>{
	boolean existsByName(String name);

}
