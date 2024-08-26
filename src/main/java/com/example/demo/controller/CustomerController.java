package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.NewCustomerStats;
import com.example.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/new-customers")
    public ResponseEntity<?> getNewCustomersOverTime(@RequestParam String interval) {
        try {
            List<NewCustomerStats> stats = customerService.getNewCustomersOverTime(interval);
            return ResponseEntity.ok(stats);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid interval: " + interval);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving new customers over time");
        }
    }
}
