package com.onlineShop.store.dto;

import java.util.Map;

import com.onlineShop.store.entities.Product;

import lombok.Data;

@Data
public class ProductDTO {
    private Product product;
    private Map<String, Object> additionalFields;
}