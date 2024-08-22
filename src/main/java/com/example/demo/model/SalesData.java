package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shopifyOrders")
public class SalesData {
    @Id
    private String id;
    private Double totalPrice; // Adjusted to match the type used in aggregation

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    // Constructors
    public SalesData() {
    }

    public SalesData(String id, Double totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }
}