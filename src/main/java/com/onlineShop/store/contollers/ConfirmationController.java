package com.onlineShop.store.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.entities.User;
import com.onlineShop.store.services.UserService;

@RestController
public class ConfirmationController {

    @Autowired
    private UserService userService;

    @GetMapping("/confirm/{token}")
    public ResponseEntity<Integer> confirmRegistration(@PathVariable String token) {
    	User user = userService.findByConfirmationToken(token);
    	if(user!=null) {
    		user.setAccountNonLocked(true);
    		userService.save(user);
    	}
    	return ResponseEntity.ok(200);
    }
}