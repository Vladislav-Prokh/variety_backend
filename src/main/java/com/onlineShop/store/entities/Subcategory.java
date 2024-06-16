package com.onlineShop.store.entities;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "subcategory")
@Data
@AllArgsConstructor
public class Subcategory {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    private String name;
	    @JsonBackReference
	    @ManyToOne
	    @JoinColumn(name = "category_id")
	    private Category category;
	    @JsonIgnore
	    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
	    private List<Product> products;

  

    @Override
	public int hashCode() {
		return Objects.hash(category, id, name, products);
	}

    public String getCategoryName() {
    	return this.category.getName();
    }
    
   
	public Subcategory(String name, Category l) {
		super();
		this.name = name;
		this.category = l;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subcategory other = (Subcategory) obj;
		return category == other.category && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(products, other.products);
	}

	public Subcategory() {
		super();
	}
	
	public Subcategory(String name) {
		this.name = name;
	}
}