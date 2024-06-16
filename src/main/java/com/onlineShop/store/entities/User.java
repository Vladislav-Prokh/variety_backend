package com.onlineShop.store.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Builder
public class User implements UserDetails  {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String username;
	@Column(unique=true)
	private String email;
	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Order> orders = new ArrayList<>();
	
	private String password;
	private boolean isAccountNonLocked;
	@Transient
	private String passwordConfirm;
	private LocalDateTime createdAt;
	@Nullable
	private String confirmationToken;

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
	 private Set<Role> roles;
	
	 public User(){
		roles = new HashSet<>();
	}
	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	      return roles.stream()
	              .map(role -> new SimpleGrantedAuthority(role.getName()))
	              .collect(Collectors.toList());
	  }
	  @Override
	  public String getUsername() {
	      return email;
	  }
	  @Override
	  public boolean isAccountNonExpired() {
	      return true;
	  }
	  @Override
	  public boolean isAccountNonLocked() {
	      return isAccountNonLocked;
	  }
	  @Override
	  public boolean isCredentialsNonExpired() {
	      return true;
	  }
	  @Override
	  public boolean isEnabled() {
	      return true;
	  }


	
}
