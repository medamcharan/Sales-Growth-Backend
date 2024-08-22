package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shopifyOrders")
public class Order {
    @Id
    private String id;
    private Date createdAt;
    private PriceSet totalPriceSet;
    private String customerId;

    // Default constructor
    public Order() {
    }

    // Constructor with parameters
    public Order(String id, Date createdAt, PriceSet totalPriceSet, String customerId) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalPriceSet = totalPriceSet;
        this.customerId = customerId ;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PriceSet getTotalPriceSet() {
        return totalPriceSet;
    }

    public void setTotalPriceSet(PriceSet totalPriceSet) {
        this.totalPriceSet = totalPriceSet;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Inner class PriceSet
    public static class PriceSet {
        private String shopMoney;

        // Default constructor
        public PriceSet() {
        }

        // Constructor with parameters
        public PriceSet(String shopMoney) {
            this.shopMoney = shopMoney;
        }

        // Getters and Setters
        public String getShopMoney() {
            return shopMoney;
        }

        public void setShopMoney(String shopMoney) {
            this.shopMoney = shopMoney;
        }
    }
}
