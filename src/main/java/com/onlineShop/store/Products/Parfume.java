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
@Table(name = "parfume")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class Parfume extends Product implements Option {

	private float volume;
	private String kind;
	private String sex;
	private String country;
	private String basic_chords;
	private String note_heart;
	private String final_note;
	private String classification_of_aroma;



	
	public Parfume() {
		super();
	}

	   public void initializeFromAdditionalFields(Map<String, Object> additionalFields) {
	        this.setVolume(Float.parseFloat(additionalFields.get("volume").toString()));
	        this.setKind(additionalFields.get("kind").toString());
	        this.setSex(additionalFields.get("sex").toString());
	        this.setCountry(additionalFields.get("country").toString());
	        this.setBasic_chords(additionalFields.get("basic_chords").toString());
	        this.setNote_heart(additionalFields.get("note_heart").toString());
	        this.setFinal_note(additionalFields.get("final_note").toString());
	        this.setClassification_of_aroma(additionalFields.get("classification_of_aroma").toString());
	    }
}
