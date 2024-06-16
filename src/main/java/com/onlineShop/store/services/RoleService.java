package com.onlineShop.store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineShop.store.entities.Role;
import com.onlineShop.store.repositories.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRep;
	
	public Role findRoleByName(String name) {
		return roleRep.findByName(name);
	}

}
