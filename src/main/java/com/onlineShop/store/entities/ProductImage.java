package com.onlineShop.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class ProductImage {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

		@ManyToOne
		@JoinColumn(name = "product_id") 
		private Product product;
		
		private String displayImageURL;
		private String deleteImageURL;
}
