package com.onlineShop.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineShop.store.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
	public Role findByName(String name);
	boolean existsByName(String name);
}
