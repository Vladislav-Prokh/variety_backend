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
@Table(name = "pc")
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class PC extends Product implements Option {
	private String processor;
    private String graphics;
    private int RAM;
    private int storage;
    private String display;
    private String motherboard;
    private String operatingSystem;
    private String powerSupply;
    private String keyboard;
    private String opticalDrive;
    private float weight;
    
    public PC() {
        super();
    }
    
    
    public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
        this.setProcessor(additionalFields.get("processor").toString());
        this.setGraphics(additionalFields.get("graphics").toString());
        this.setRAM(Integer.parseInt(additionalFields.get("RAM").toString()));
        this.setStorage(Integer.parseInt(additionalFields.get("storage").toString()));
        this.setDisplay(additionalFields.get("display").toString());
        this.setMotherboard(additionalFields.get("motherboard").toString());
        this.setOperatingSystem(additionalFields.get("operatingSystem").toString());
        this.setPowerSupply(additionalFields.get("powerSupply").toString());
        this.setKeyboard(additionalFields.get("keyboard").toString());
        this.setOpticalDrive(additionalFields.get("opticalDrive").toString());
        this.setWeight(Float.parseFloat(additionalFields.get("weight").toString()));
    }
}
