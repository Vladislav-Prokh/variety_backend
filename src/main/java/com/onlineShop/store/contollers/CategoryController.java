package com.onlineShop.store.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.entities.Category;
import com.onlineShop.store.entities.Subcategory;
import com.onlineShop.store.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addCategory(@RequestBody Category category) {
		categoryService.addCategory(category);
	}
	
	@RequestMapping(value = "/add/{categoryId}/subcategory/{categoryName}", method = RequestMethod.POST)
	public void addCategory(@PathVariable("categoryName") String categoryName,@PathVariable("categoryId")  Integer categoryId) {
		categoryService.addSubCategory(categoryName,categoryId);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void deleteCategory(@PathVariable Integer id) {
		categoryService.deleteCategoryById((long)id);
	}
	
	
	@RequestMapping(value = "/delete/subcategory/{id}", method = RequestMethod.POST)
	public void deleteSubCategory(@PathVariable Integer id) {
		categoryService.deleteSubCategoryById((long)id);
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Category> getAll() {
		return categoryService.findAll();
	}
	@RequestMapping(value = "/getSubcategories", method = RequestMethod.GET)
	public List<Subcategory> getSubcategoriesByCategory(@RequestParam("categoryName") String categoryName) {
	    return categoryService.findChildSubcategoriesByCategory(categoryName);
	}
}
