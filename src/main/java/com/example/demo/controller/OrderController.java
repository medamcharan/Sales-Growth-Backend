package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SalesData;
import com.example.service.OrderService;

@RestController
@RequestMapping("/sales")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/total")
    public List<SalesData> getTotalSales(@RequestParam(defaultValue = "daily") String interval) {
        return orderService.getTotalSales(interval);
    }
}
