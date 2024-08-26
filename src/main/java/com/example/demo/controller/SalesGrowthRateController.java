package com.example.demo.controller;

import com.example.service.SalesGrowthRateService;
import com.example.demo.model.SalesGrowthRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalesGrowthRateController {

    @Autowired
    private SalesGrowthRateService salesGrowthRateService;

    @GetMapping("/sales-growth-rate")
    public List<SalesGrowthRate> getSalesGrowthRate(@RequestParam String interval) {
        return salesGrowthRateService.calculateGrowthRate(interval);
    }
}
