package com.onlineShop.store.Products;


import java.util.Map;

import com.onlineShop.store.Interfaces.Option;
import com.onlineShop.store.entities.Product;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "laptop")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class Laptop extends Product implements Option{
	
	private String processor;
	private String GNU;
	private String RAM;
	private String memory;
	private String motherboard;

	public Laptop() {
		super();
	}
	
	   public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
	        this.setProcessor(additionalFields.get("processor").toString());
	        this.setGNU(additionalFields.get("graphics").toString());
	        this.setRAM(additionalFields.get("RAM").toString());
	        this.setMemory(additionalFields.get("storage").toString());
	        this.setMotherboard(additionalFields.get("motherboard").toString());
	    }
}
