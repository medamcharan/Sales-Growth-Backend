package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.NewCustomerStats;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;

@Service
public class CustomerService {

    @Autowired
    private MongoDatabase mongoDatabase;

    public List<NewCustomerStats> getNewCustomersOverTime(String interval) {
        try {
            List<Bson> pipeline = getAggregationPipeline(interval);
            MongoCollection<Document> collection = mongoDatabase.getCollection("shopifyCustomers");
            List<NewCustomerStats> results = new ArrayList<>();
            
            // Perform the aggregation and map results
            collection.aggregate(pipeline).forEach(doc -> {
                NewCustomerStats stats = new NewCustomerStats();
                stats.setDate(doc.getString("_id"));
                stats.setNewCustomers(doc.getInteger("newCustomers", 0));
                results.add(stats);
            });
            
            return results;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving new customers over time", e);
        }
    }

    private List<Bson> getAggregationPipeline(String interval) {
    List<Bson> pipeline = new ArrayList<>();

    switch (interval.toLowerCase()) {
        case "daily" -> pipeline.add(group(
                new Document("date", new Document("$dateToString", new Document("format", "%Y-%m-%d").append("date", "$createdAt"))),
                sum("newCustomers", 1)
            ));
        case "monthly" -> pipeline.add(group(
                new Document("date", new Document("$dateToString", new Document("format", "%Y-%m").append("date", "$createdAt"))),
                sum("newCustomers", 1)
            ));
        case "quarterly" -> pipeline.add(group(
                new Document("date", new Document("$concat", Arrays.asList(
                    new Document("$dateToString", new Document("format", "%Y").append("date", "$createdAt")),
                    "-Q",
                    new Document("$ceil", new Document("$divide", Arrays.asList(
                        new Document("$month", "$createdAt"),
                        3
                    )))
                ))),
                sum("newCustomers", 1)
            ));
        case "yearly" -> pipeline.add(group(
                new Document("date", new Document("$dateToString", new Document("format", "%Y").append("date", "$createdAt"))),
                sum("newCustomers", 1)
            ));
        default -> throw new IllegalArgumentException("Invalid interval: " + interval);
    }

    return pipeline;
}

}
