package com.onlineShop.store.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineShop.store.Exceptions.UnauthorizedException;
import com.onlineShop.store.Exceptions.UserHasAlreadyExistsException;
import com.onlineShop.store.dto.JwtAuthenticationResponse;
import com.onlineShop.store.dto.SignInRequest;
import com.onlineShop.store.dto.SignUpRequest;
import com.onlineShop.store.entities.Role;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


  private final UserService userService;
  private final JwtService jwtService;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  public User signup(SignUpRequest request) {
	 
		  
		  if(userService.ifExistsUserByEmail(request.getEmail())) {
			  throw new UserHasAlreadyExistsException("UserHasAlreadyExists");
		  }
		  
		  
	 Role userRole = roleService.findRoleByName("ROLE_USER");

     User user = User
                  .builder()
                  .email(request.getEmail())
                  .password(passwordEncoder.encode(request.getPassword()))
                  .roles(Set.of(userRole))
                  .isAccountNonLocked(false)
                  .build();

      return user;
	 
  }


  public JwtAuthenticationResponse signin(SignInRequest request) {
	  try {
		  
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword() ));
      
      var user = userRepository.findByEmail(request.getEmail())
              .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
      var jwt = jwtService.generateToken(user);
      
      List<String> rolesOfUser = user.getAuthorities().stream()
              .map(authority -> authority.getAuthority())
              .collect(Collectors.toList());
  
      return JwtAuthenticationResponse.builder().token(jwt).roles(rolesOfUser).user_id(user.getId()).build();
      
	  } catch (AuthenticationException e) {
	        throw new UnauthorizedException("Unauthorized");
	  }
  }


	public void saveNewRegisteredUser(User newUser) {
		 if(userService.ifExistsUserByEmail(newUser.getEmail())) {
			  throw new UserHasAlreadyExistsException("UserHasAlreadyExists");
		  }
		 else {
			 this.userService.save(newUser);
		 }
	}
	  
  

}

