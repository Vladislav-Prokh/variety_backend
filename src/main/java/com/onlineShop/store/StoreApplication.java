package com.onlineShop.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.onlineShop.store.configuration.ServerInitializer;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class StoreApplication {
	
	@Autowired
	ServerInitializer initializer;
	
	private static String fileCategoryAndSubcategoryInitilizerPath="/src/main/resources/categoryAndSubcategotyInitilizer.txt";

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
		if(args.length >=2) {
			fileCategoryAndSubcategoryInitilizerPath = args[0];
		}

	}
	
	  @PostConstruct
	    public void initialize() {
	        try {
	            initializer.initializeRolesInBD();
	            initializer.initializeCategoryAndSubcategory(fileCategoryAndSubcategoryInitilizerPath);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}

