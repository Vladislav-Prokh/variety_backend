package com.onlineShop.store.entities;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    @JsonIgnoreProperties("category")
	    @JsonManagedReference
	    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	    private List<Subcategory> subcategories;


	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Category category = (Category) o;
	        return id.equals(category.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

		public Category(Long id, String name, List<Subcategory> subcategories) {
			super();
			this.id = id;
			this.name = name;
			this.subcategories = subcategories;
		}

		public Category() {
			super();
		}  
		
		public Category(String name) {
			this.name = name;
		}
		

}