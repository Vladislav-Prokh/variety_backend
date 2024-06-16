package com.onlineShop.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rate_id;

    private int rating;
    @JsonIgnore
    @ManyToOne
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id" ,unique=false) 
    private User user;

	public ProductRating(Long id, int rating, Product product, User user) {
		super();
		this.rate_id = id;
		this.rating = rating;
		this.product = product;
		this.user = user;
	}

	public ProductRating() {
		super();
	}
    
    
}