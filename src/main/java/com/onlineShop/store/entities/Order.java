package com.onlineShop.store.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		Long id;
	
		@Column(name = "customer_name")
	    private String customerName;
		@Column(name = "customer_second_name")
		private String customerSecondName;
		@Column(name = "customer_last_name")
		private String customerLastName;
		
		
	    @Column(name = "city_to_deliver")
	    private String cityToDeliver;
	    
	    @Column(name = "post_office_to_deliver")
	    private String postOfficeToDeliver;
	    @JsonIgnore
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<OrderedProduct> orderedProducts = new ArrayList<>();
	    
	    @Column(name = "order_status")
	    @Enumerated(EnumType.STRING)
	    private OrderStatus status;
	    
	    private LocalDateTime date = LocalDateTime.now(); 
	    
	    @Column(name = "total_cost")
	    private float totalCost;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = true)
		 @JsonBackReference
	    private User user;
	
    

}
