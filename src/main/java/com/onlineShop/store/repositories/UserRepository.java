package com.onlineShop.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineShop.store.entities.User;


public interface UserRepository extends JpaRepository <User,Long> {
	
	Optional<User> findByUsername(String username);
	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);
	Optional<User> findByEmail(String username);
	List<User> findByEmailOrUsername(String email, String username); 
	public boolean deleteByUsername(String name);
	User findByConfirmationToken(String token);
	
	@Query("SELECT u FROM User u WHERE u.email LIKE %:email% OR u.username LIKE %:username%")
    List<User> findByEmailOrUsernameContaining(@Param("email") String email, @Param("username") String username);
	
}
