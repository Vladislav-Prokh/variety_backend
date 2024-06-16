package com.onlineShop.store.dto;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatistics {
    private Date date;
    private Long orderCount;
    private Double totalCost;
    
        

}
