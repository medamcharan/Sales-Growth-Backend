package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria; // Add this import
import org.springframework.stereotype.Service;

import com.example.demo.model.SalesData;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SalesData> getTotalSales(String interval) {
        Aggregation aggregation = switch (interval) {
            case "daily" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)), // Ensure 'created_at' exists
                Aggregation.project("total_price_set.shop_money.amount")
                    .andExpression("dateToString('%Y-%m-%d', created_at)").as("date")
                    .and("total_price_set.shop_money.amount").as("totalSales"),
                Aggregation.group("date")
                    .sum("totalSales").as("totalSales")
            );
            case "monthly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)), // Ensure 'created_at' exists
                Aggregation.project("total_price_set.shop_money.amount")
                    .andExpression("dateToString('%Y-%m', created_at)").as("date")
                    .and("total_price_set.shop_money.amount").as("totalSales"),
                Aggregation.group("date")
                    .sum("totalSales").as("totalSales")
            );
            case "quarterly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)), // Ensure 'created_at' exists
                Aggregation.project("total_price_set.shop_money.amount")
                    .andExpression("concat(dateToString('%Y-', created_at), 'Q', (month(created_at) - 1) / 3 + 1)").as("date")
                    .and("total_price_set.shop_money.amount").as("totalSales"),
                Aggregation.group("date")
                    .sum("totalSales").as("totalSales")
            );
            case "yearly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)), // Ensure 'created_at' exists
                Aggregation.project("total_price_set.shop_money.amount")
                    .andExpression("dateToString('%Y', created_at)").as("date")
                    .and("total_price_set.shop_money.amount").as("totalSales"),
                Aggregation.group("date")
                    .sum("totalSales").as("totalSales")
            );
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };

        AggregationResults<SalesData> result = mongoTemplate.aggregate(aggregation, "shopifyOrders", SalesData.class);
        return result.getMappedResults();
    }
}
