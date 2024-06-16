package com.onlineShop.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineShop.store.entities.Subcategory;
import com.onlineShop.store.repositories.SubCategoryRepository;

@Service
public class SubcategoryService {
	@Autowired
	private SubCategoryRepository subCatRep;
	
	
	public List<Subcategory> findAllSubs(){
		return subCatRep.findAll();
	}
	
	public void deleteByid(Long id) {
		 subCatRep.deleteById(id);
	}
	
	public Optional<Subcategory> findById(Long id) {
		return subCatRep.findById(id);
	}
}
