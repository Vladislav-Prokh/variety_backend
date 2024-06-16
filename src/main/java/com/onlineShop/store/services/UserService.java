package com.onlineShop.store.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineShop.store.entities.Role;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleService roleService;

  public UserDetailsService userDetailsService() {
      return new UserDetailsService() {
          @Override
          public UserDetails loadUserByUsername(String username) {
              return (UserDetails) userRepository.findByEmail(username)
                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
          }
      };
  }
  @Transactional
  public User save(User newUser) {
    if (newUser.getId() == null) {
      newUser.setCreatedAt(LocalDateTime.now());
    }

    return userRepository.save(newUser);
  }
  public User findUserByEmail(String email) {
	  return userRepository.findByEmail(email).orElse(null);
  }
	
	  public boolean ifExistsUserByEmail(String email) {
		  return userRepository.existsByEmail(email);
	  }
	  public List<User> findAllUsers(){
		  return userRepository.findAll();
	  }
	  public List<User> findUserByNameOrEmail(String param){
		  return userRepository.findByEmailOrUsernameContaining(param, param);
	  }
	  
	  public void deleteById(Long id) {
		 userRepository.deleteById(id);; 
	  }
	  public void banUserById(Long id) {
		  User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));	  
		  user.setAccountNonLocked(false);
		  userRepository.save(user);
		  
	  }
		public void unBanUserById(Long id) {
			  User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));	  
			  user.setAccountNonLocked(true);
			  userRepository.save(user);
		}
		public void giveRightById(Long userId,String roleName) {
			Role role = roleService.findRoleByName(roleName);
			User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
			if(role != null) {
				user.getRoles().add((role));
				userRepository.save(user);
				
			}
	
		}
		public void deleteRightById(Long userId,String roleName) {
			Role role = roleService.findRoleByName(roleName);
			User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
			if(role!=null) {
				 user.getRoles().remove(role);
			     userRepository.save(user);
			}
		}
		
		public User findUserById(Long id) {
			return userRepository.findById(id).get();
		}
		
		public Long getCurrentUserId() {
			  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        if (authentication != null && authentication.isAuthenticated()) {
		            return ((User) authentication.getPrincipal()).getId(); 
		        } else {
		            return -1L; 
		        }
		}
		
		public User findByConfirmationToken(String Token) {
			return this.userRepository.findByConfirmationToken(Token);
		}
}

