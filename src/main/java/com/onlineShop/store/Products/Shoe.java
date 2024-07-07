package com.onlineShop.store.Products;

import java.util.Map;

import com.onlineShop.store.entities.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "shoes")
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class Shoe extends Product{
	
	public Shoe() {

	}
	private String code;
	private String size;
	private String country;
	private String material;
	
	public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
		this.setSize(additionalFields.get("size").toString());
		this.setMaterial(additionalFields.get("material").toString());
		this.setCode(additionalFields.get("code").toString());
        this.setCountry(additionalFields.get("country").toString());
	}
	


}
