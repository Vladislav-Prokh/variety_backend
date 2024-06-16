package com.onlineShop.store.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.onlineShop.store.entities.Category;
import com.onlineShop.store.entities.Role;
import com.onlineShop.store.entities.Subcategory;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.CategoryRepository;
import com.onlineShop.store.repositories.RoleRepository;
import com.onlineShop.store.repositories.SubCategoryRepository;


@Configuration
public class ServerInitializer {

	@Autowired
	private RoleRepository roleRep;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subcategoryRepository;
	
	
	 public void initializeRolesInBD() {
		Set<User> users = new HashSet<User>();
		Role roleUser = new Role(1L,"ROLE_USER",users);
		Role roleAdmin = new Role(2L,"ROLE_ADMIN",users);
		Role roleModerator = new Role(3L,"ROLE_MODERATOR",users);
        addRoleIfNotExists(roleUser);
        addRoleIfNotExists(roleAdmin);
        addRoleIfNotExists(roleModerator);
		
	}
	 private void addRoleIfNotExists(Role role) {
	        if (!roleRep.existsByName(role.getName())) {
	            roleRep.save(role);
	     }
	 }
	 public void initializeCategoryAndSubcategory(String fileCategoryAndSubcategoryInitilizerPath) {
		 
		 String currentDirectory = System.getProperty("user.dir");

	     String relativePath = fileCategoryAndSubcategoryInitilizerPath;

	     Path filePath = Paths.get(currentDirectory, relativePath);
	     
		List<Category> categories = new ArrayList<Category>();
	     
	 	List<Subcategory> subcategories = new ArrayList<Subcategory>();
	        
	     try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
	            String categoryName;
	            while ((categoryName = reader.readLine()) != null) {
	            	
	            	Category category = new Category(categoryName);
	            	
	            	if(categoryRepository.existsByName(categoryName)) {
	            		reader.readLine();
	            		continue;
	            	}
	           
	            	categories.add(category);
	            
	            	String subcategoriesStr = reader.readLine();
	     	       
	            	String[] subcategoryNames = new String[0];
	            	
	            	if(subcategoriesStr != null) {
	            		subcategoryNames = subcategoriesStr.split("/");
	            	}
	            	
	            	for(int i = 0;i < subcategoryNames.length;i++) {
	            		
	            		if(subcategoryRepository.existsByName(subcategoryNames[i])){
	            			continue;
	            		}
	            		
	            		Subcategory subcategory = new Subcategory(subcategoryNames[i]);
	            		subcategory.setCategory(category);
	            		subcategories.add(subcategory);
	            	}
	          
	            }
	           
            	categoryRepository.saveAll(categories);
            	subcategoryRepository.saveAll(subcategories);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
}
