package com.onlineShop.store.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.onlineShop.store.entities.Role;
import com.onlineShop.store.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private  RoleService roleService;
	
	  public LoginSuccessHandler() {

	        super.setDefaultTargetUrl("http://variety.in.net:8086/auth/google/success");

	  }

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
	        OAuth2User userProfile = (OAuth2User) authentication.getPrincipal();

	        req.getSession().setAttribute("loggedin", true);
	        req.getSession().setAttribute("name", userProfile.getAttributes().get("name"));
	        req.getSession().setAttribute("email", userProfile.getAttributes().get("email"));
	        
	        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
	                userProfile.getAttribute("email"),
	                "", 
	                new ArrayList<>()
	        );
	        String jwtToken = jwtService.generateToken(userDetails);
	        
	        req.getSession().setAttribute("jwtToken", jwtToken);
	        
	        User user = userService.findUserByEmail(userProfile.getAttributes().get("email").toString());
	        if(user == null) {
	            Role userRole = roleService.findRoleByName("ROLE_USER");

	            user = User
	                    .builder()
	                    .email(userProfile.getAttributes().get("email").toString())
	                    .roles(Set.of(userRole))
	                    .isAccountNonLocked(false)
	                    .build();

	            userService.save(user);
	        }
	        
	        Set<Role> authorities = user.getRoles();
	        Long userId = user.getId();

	        String redirectUrl = getRedirectUrlWithParams(jwtToken, userId, userProfile.getAttributes().get("email").toString(), authorities);
	        getRedirectStrategy().sendRedirect(req, response, redirectUrl);
	        
	        super.onAuthenticationSuccess(req, response, authentication);
	    }
	    
	    private String getRedirectUrlWithParams(String jwtToken, Long userId, String email, Set<Role> roles) throws UnsupportedEncodingException {
	        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
	        String rolesParam = roles.stream()
	                                 .map(Role::getName)
	                                 .collect(Collectors.joining(","));
	        String encodedRoles = URLEncoder.encode(rolesParam, StandardCharsets.UTF_8.toString());
	        
	        return getDefaultTargetUrl() + "?jwtToken=" + jwtToken + "&email=" + encodedEmail + "&userId=" + userId + "&roles=" + encodedRoles;
	    }
}