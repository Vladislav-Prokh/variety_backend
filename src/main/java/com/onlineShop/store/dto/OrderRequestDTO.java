package com.onlineShop.store.dto;

import java.util.List;


import lombok.Data;

@Data
public class OrderRequestDTO {
	private OrderInfoDTO orderInfo;
	private List<Long> productIds;
	private List<Integer> productAmountInOrderByIds;
}
