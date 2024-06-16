package com.onlineShop.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineShop.store.entities.Category;
import com.onlineShop.store.entities.Subcategory;
import com.onlineShop.store.repositories.CategoryRepository;
import com.onlineShop.store.repositories.SubCategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRep;
	
	@Autowired 
	private SubCategoryRepository subcategoryRep;
	
	
	public void deleteCategoryById(Long id) {
		this.categoryRep.deleteById(id);
	}
	
	public void deleteCategoryByName(String name) {
		categoryRep.deleteCategoryByName(name);
	}
	
	public boolean addCategory(Category category) {
		if(categoryRep.save(category)!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	public Optional<Category> findCategoryById(Long id) {
		return categoryRep.findById(id);
	}
	
	public List<Category> findAll(){
		return this.categoryRep.findAll();
	}
	public Category findCategoryByName(String name) {
		return this.categoryRep.findByName(name);
	}
	
	public List<Subcategory> findChildSubcategoriesByCategory(String name){
		Category category = findCategoryByName(name);
		return category.getSubcategories();
	}

	public void deleteSubCategoryById(long id) {
	
		subcategoryRep.deleteById(id);
	}

	public void addSubCategory(String categoryName, Integer categoryId) {
		Category category = categoryRep.findById((long)categoryId).get();
		Subcategory newSubCaqtegory = new Subcategory(categoryName);
		newSubCaqtegory.setCategory(category);
		subcategoryRep.save(newSubCaqtegory);
		
	}
	

}
