package com.onlineShop.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onlineShop.store.entities.OrderStatus;
import com.onlineShop.store.entities.OrderedProduct;
import com.onlineShop.store.entities.User;

import jakarta.persistence.EntityManager;

@Component
public class UserUtils {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private EntityManager entityManager;
	
	public boolean checkIfUserByEmailBoughtProductById(Long productId,String userEmail) {
		 User currentUser = userService.findUserByEmail(userEmail);
		 String jpql = "SELECT op FROM OrderedProduct op JOIN op.order o WHERE o.user.id = :userId AND o.status = :orderStatus AND op.product.id = :productId";
	     List<OrderedProduct> orderedProductsByCurrenetUser = entityManager.createQuery(jpql, OrderedProduct.class)
	    		.setParameter("userId", currentUser.getId())
	            .setParameter("orderStatus", OrderStatus.SHIPPED)
	            .setParameter("productId", productId)
	            .getResultList();
	     if(orderedProductsByCurrenetUser.size() ==0 ) {
	    	 return false;
	     }
	     
	     return true;
	}
	

}
