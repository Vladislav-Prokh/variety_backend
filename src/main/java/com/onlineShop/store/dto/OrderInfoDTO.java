package com.onlineShop.store.dto;

import java.time.LocalDate;

import com.onlineShop.store.entities.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class OrderInfoDTO {
	
    private String customerName;

	private String customerSecondName;

	private String customerLastName;

    private String cityToDeliver;
    

    private String postOfficeToDeliver;
    

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    private LocalDate date;

    private float totalCost;
    
    private Integer userId;
    
    
}
