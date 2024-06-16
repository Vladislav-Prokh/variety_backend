 package com.onlineShop.store.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.Interfaces.Option;
import com.onlineShop.store.services.ProductService;


@RestController
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public List<Option> home() {
		return productService.findAllProducts(); 
	}


}
