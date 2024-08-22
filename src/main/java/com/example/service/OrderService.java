package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo.model.SalesData;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SalesData> getTotalSales(String interval) {
        Aggregation aggregation = switch (interval) {
            case "daily" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id")
                    .andExpression("dateToString('%Y-%m-%d', toDate(created_at))").as("date")
                    .andExpression("toDouble(total_price)").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
                    .first("id").as("id")
            );
            case "monthly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id")
                    .andExpression("dateToString('%Y-%m', toDate(created_at))").as("date")
                    .andExpression("toDouble(total_price)").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
                    .first("id").as("id")
            );
            case "quarterly" -> Aggregation.newAggregation(
    Aggregation.match(Criteria.where("created_at").exists(true)),
    Aggregation.project("id")
        .andExpression("concat(dateToString('%Y', toDate(created_at)), 'Q', toString((month(toDate(created_at)) - 1) / 3 + 1))").as("date")
        .andExpression("toDouble(total_price)").as("totalPrice"),
    Aggregation.group("date")
        .sum("totalPrice").as("totalPrice")
        .first("id").as("id")
);

            case "yearly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id")
                    .andExpression("dateToString('%Y', toDate(created_at))").as("date")
                    .andExpression("toDouble(total_price)").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
                    .first("id").as("id")
            );
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };

        AggregationResults<SalesData> result = mongoTemplate.aggregate(aggregation, "shopifyOrders", SalesData.class);
        return result.getMappedResults();
    }
}
