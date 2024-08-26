package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo.model.SalesData;
import com.example.demo.model.SalesGrowthRate;

@Service
public class SalesGrowthRateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SalesGrowthRate> calculateGrowthRate(String interval) {
        Aggregation salesAggregation = getAggregationPipeline(interval);

        // Map the aggregation result to SalesData class
        AggregationResults<SalesData> salesResult = mongoTemplate.aggregate(salesAggregation, "shopifyOrders", SalesData.class);
        List<SalesData> salesData = salesResult.getMappedResults();

        // Process the aggregated data to calculate growth rates
        return calculateGrowthRates(salesData);
    }

    private Aggregation getAggregationPipeline(String interval) {
        // Validate the interval parameter
        if (!List.of("daily", "monthly", "quarterly", "yearly").contains(interval)) {
            throw new IllegalArgumentException("Invalid interval: " + interval);
        }

        return switch (interval) {
            case "daily" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("totalPrice")
                    .andExpression("dateToString('%Y-%m-%d', toDate(created_at))").as("date")
                    .andExpression("totalPrice / 100").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
            );
            case "monthly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("totalPrice")
                    .andExpression("dateToString('%Y-%m', toDate(created_at))").as("date")
                    .andExpression("totalPrice / 100").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
            );
            case "quarterly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("totalPrice")
                    .andExpression("concat(dateToString('%Y', toDate(created_at)), 'Q', toString((month(toDate(created_at)) - 1) / 3 + 1))").as("date")
                    .andExpression("totalPrice / 100").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
            );
            case "yearly" -> Aggregation.newAggregation(
                Aggregation.match(Criteria.where("created_at").exists(true)),
                Aggregation.project("totalPrice")
                    .andExpression("dateToString('%Y', toDate(created_at))").as("date")
                    .andExpression("totalPrice / 100").as("totalPrice"),
                Aggregation.group("date")
                    .sum("totalPrice").as("totalPrice")
            );
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };
    }

    private List<SalesGrowthRate> calculateGrowthRates(List<SalesData> salesData) {
        // Sort the data by date
        List<SalesData> sortedSalesData = salesData.stream()
            .sorted((d1, d2) -> d1.getId().compareTo(d2.getId())) // Assuming ID is the date
            .collect(Collectors.toList());
    
        List<SalesGrowthRate> growthRates = new ArrayList<>();
        Double previousTotalPrice = null;
    
        // Iterate through the sorted data and calculate growth rates
        for (int i = 0; i < sortedSalesData.size(); i++) {
            SalesData currentData = sortedSalesData.get(i);
            Double currentTotalPrice = currentData.getTotalPrice();
            String date = currentData.getId();
    
            Double growthRate = 0.0;
    
            // Ensure currentTotalPrice is not null to avoid further issues
            if (currentTotalPrice == null) {
                currentTotalPrice = 0.0;
            }
    
            if (i == 0) {
                // For the first period, the growth rate is 0
                growthRate = 0.0;
            } else if (previousTotalPrice != null && previousTotalPrice != 0) {
                // Calculate growth rate if previousTotalPrice is not null or zero
                growthRate = ((currentTotalPrice - previousTotalPrice) / previousTotalPrice) * 100;
            }
    
            // Create a new SalesGrowthRate object
            SalesGrowthRate growthRateData = new SalesGrowthRate();
            growthRateData.setDate(date);
            growthRateData.setGrowthRate(growthRate);
    
            // Add the growth rate data to the list
            growthRates.add(growthRateData);
    
            // Update the previous total price for the next iteration
            previousTotalPrice = currentTotalPrice;
        }
    
        return growthRates;
    }
}
