package com.onlineShop.store.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.entities.Order;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.services.OrderService;
import com.onlineShop.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/all",method = RequestMethod.GET)
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}
	
	@RequestMapping(value = "/{email}",method = RequestMethod.POST)
	public List<User> findUserByNameOrEmail(@PathVariable("email")String email) {
		return userService.findUserByNameOrEmail(email);
	}
	
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
	public void deleteUserById(@PathVariable("id")Long id) {
		userService.deleteById(id);
	}

	@RequestMapping(value = "/giveRights/{id}/{right}",method = RequestMethod.POST)
	public void giveUserRightById(@PathVariable("id")Long id,@PathVariable("right") String right) {
		userService.giveRightById(id,right);
	}
	@RequestMapping(value = "/deleteRights/{id}/{right}",method = RequestMethod.POST)
	public void deleteUserRightById(@PathVariable("id")Long id,@PathVariable("right") String right) {
		userService.deleteRightById(id,right);
	}
	
	@RequestMapping(value = "/ban/{id}",method = RequestMethod.POST)
	public void banUserById(@PathVariable("id")Long id) {
		userService.banUserById(id);
	}

	@RequestMapping(value = "/unban/{id}",method = RequestMethod.POST)
	public void unBanUserById(@PathVariable("id")Long id) {
		userService.unBanUserById(id);
	}
	
	@RequestMapping(value = "/{user_id}/orders", method = RequestMethod.GET)
	public List<Order> findOrdersByUserID(@PathVariable ("user_id")Long user_id) {
		return orderService.findAllByUser(user_id);
	}
	
	
	  @RequestMapping(value = "/accounts/{user_id}", method = RequestMethod.GET)
	    public ResponseEntity<?> findUserById(@PathVariable("user_id") Long user_id) {
	        Long currentUserId = userService.getCurrentUserId();
	        if (currentUserId != null && currentUserId.equals(user_id)) {
	            User user = userService.findUserById(user_id);
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
	        }
	    }
		
		
}
