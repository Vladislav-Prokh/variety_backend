package com.onlineShop.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineShop.store.entities.Category;

public interface CategoryRepository extends JpaRepository <Category,Long>{
	
	public void deleteCategoryByName(String Name);
	boolean existsByName(String name);
	public Category findByName(String name);

}
