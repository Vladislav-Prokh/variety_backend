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
@Table(name = "smartphone")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class Smartphone extends Product implements Option{
	

	private int RAM;
	private String processor;
	private float diagonal;
	private String display;
	private float camera;
	private float memory;
	private String screenType;
	public Smartphone() {
		super();
	}


    public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
        this.setRAM(Integer.parseInt(additionalFields.get("RAM").toString()));
        this.setProcessor(additionalFields.get("processor").toString());
        this.setDiagonal(Float.parseFloat(additionalFields.get("diagonal").toString()));
        this.setDisplay(additionalFields.get("display").toString());
        this.setCamera(Float.parseFloat(additionalFields.get("camera").toString()));
        this.setMemory(Float.parseFloat(additionalFields.get("storage").toString()));
        this.setScreenType(additionalFields.get("screenType").toString());
    }

}
