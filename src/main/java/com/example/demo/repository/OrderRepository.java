package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCreatedAtBetween(Date startDate, Date endDate);
}
