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
                Aggregation.project("id", "email", "gateway", "total_price", "subtotal_price", "total_weight",
                    "total_tax", "taxes_included", "currency", "financial_status", "confirmed", "processing_method",
                    "presentment_currency", "total_line_items_price_set")
                    .andExpression("dateToString('%Y-%m-%d', toDate(created_at))").as("date"),
                Aggregation.group("date")
                    .sum("total_line_items_price_set.shop_money.amount").as("totalSales")
                    .first("id").as("id")
                    .first("email").as("email")
                    .first("gateway").as("gateway")
                    .first("total_price").as("total_price")
                    .first("subtotal_price").as("subtotal_price")
                    .first("total_weight").as("total_weight")
                    .first("total_tax").as("total_tax")
                    .first("taxes_included").as("taxes_included")
                    .first("currency").as("currency")
                    .first("financial_status").as("financial_status")
                    .first("confirmed").as("confirmed")
                    .first("processing_method").as("processing_method")
                    .first("presentment_currency").as("presentment_currency")
            );
            case "monthly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id", "email", "gateway", "total_price", "subtotal_price", "total_weight",
                    "total_tax", "taxes_included", "currency", "financial_status", "confirmed", "processing_method",
                    "presentment_currency", "total_line_items_price_set")
                    .andExpression("dateToString('%Y-%m', toDate(created_at))").as("date"),
                Aggregation.group("date")
                    .sum("total_line_items_price_set.shop_money.amount").as("totalSales")
                    .first("id").as("id")
                    .first("email").as("email")
                    .first("gateway").as("gateway")
                    .first("total_price").as("total_price")
                    .first("subtotal_price").as("subtotal_price")
                    .first("total_weight").as("total_weight")
                    .first("total_tax").as("total_tax")
                    .first("taxes_included").as("taxes_included")
                    .first("currency").as("currency")
                    .first("financial_status").as("financial_status")
                    .first("confirmed").as("confirmed")
                    .first("processing_method").as("processing_method")
                    .first("presentment_currency").as("presentment_currency")
            );
            case "quarterly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id", "email", "gateway", "total_price", "subtotal_price", "total_weight",
                    "total_tax", "taxes_included", "currency", "financial_status", "confirmed", "processing_method",
                    "presentment_currency", "total_line_items_price_set")
                    .andExpression("concat(dateToString('%Y-', toDate(created_at)), 'Q', (month(toDate(created_at)) - 1) / 3 + 1)").as("date"),
                Aggregation.group("date")
                    .sum("total_line_items_price_set.shop_money.amount").as("totalSales")
                    .first("id").as("id")
                    .first("email").as("email")
                    .first("gateway").as("gateway")
                    .first("total_price").as("total_price")
                    .first("subtotal_price").as("subtotal_price")
                    .first("total_weight").as("total_weight")
                    .first("total_tax").as("total_tax")
                    .first("taxes_included").as("taxes_included")
                    .first("currency").as("currency")
                    .first("financial_status").as("financial_status")
                    .first("confirmed").as("confirmed")
                    .first("processing_method").as("processing_method")
                    .first("presentment_currency").as("presentment_currency")
            );
            case "yearly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("id", "email", "gateway", "total_price", "subtotal_price", "total_weight",
                    "total_tax", "taxes_included", "currency", "financial_status", "confirmed", "processing_method",
                    "presentment_currency", "total_line_items_price_set")
                    .andExpression("dateToString('%Y', toDate(created_at))").as("date"),
                Aggregation.group("date")
                    .sum("total_line_items_price_set.shop_money.amount").as("totalSales")
                    .first("id").as("id")
                    .first("email").as("email")
                    .first("gateway").as("gateway")
                    .first("total_price").as("total_price")
                    .first("subtotal_price").as("subtotal_price")
                    .first("total_weight").as("total_weight")
                    .first("total_tax").as("total_tax")
                    .first("taxes_included").as("taxes_included")
                    .first("currency").as("currency")
                    .first("financial_status").as("financial_status")
                    .first("confirmed").as("confirmed")
                    .first("processing_method").as("processing_method")
                    .first("presentment_currency").as("presentment_currency")
            );
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };

        AggregationResults<SalesData> result = mongoTemplate.aggregate(aggregation, "shopifyOrders", SalesData.class);
        return result.getMappedResults();
    }
}
