package com.example.eseshopsearch.entity;

import lombok.Data;

@Data
public class Product {
    private String productId;
    private String productName;
    private Double price;
    private String categoryId;
    private String categoryName;
    private String brand;
    private Integer sales;
    private Integer stock;
    private String description;
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
}