package com.onlineShop.store.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.dto.JwtAuthenticationResponse;
import com.onlineShop.store.dto.SignInRequest;
import com.onlineShop.store.dto.SignUpRequest;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.services.AuthenticationService;
import com.onlineShop.store.services.EmailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/registration")
    public void signup(@RequestBody SignUpRequest request) throws MessagingException {
    	User newUser =  authenticationService.signup(request);
    	String userConfirmationToken = emailService.sendRegistrationConfirmationEmail(newUser);
    	newUser.setConfirmationToken(userConfirmationToken);
    	this.authenticationService.saveNewRegisteredUser(newUser);
    }
   
    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}