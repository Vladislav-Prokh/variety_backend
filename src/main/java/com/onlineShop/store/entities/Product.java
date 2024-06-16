package com.onlineShop.store.entities;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.onlineShop.store.Interfaces.Option;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;



@Data
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Option{
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch =FetchType.LAZY)
	private List<OrderedProduct> orderedProducts;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String productName;
    private String description;
    private float price;
    private String brand  = "none brand";
    private List<String> colors;
  

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
 
	private String displayImageURL="https://i.ibb.co/R0bywzW/istockphoto-990697446-612x612.jpg";
    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> images;

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + ", description=" + description + ", price="
                + price + ", brand=" + brand + ", color=" + colors + ", subcategory=" + subcategory + "]";
    }

    public Product() {
        super();
    }

	
}