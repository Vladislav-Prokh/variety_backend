package com.onlineShop.store.Products;

import java.util.Map;

import com.onlineShop.store.Interfaces.Option;
import com.onlineShop.store.entities.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class Accessory extends Product implements Option{
	private String material;
	private String country;
	
	public Accessory(){
		
	}
	
	public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
        this.setMaterial(additionalFields.get("material").toString());
        this.setCountry(additionalFields.get("country").toString());
    }
}
