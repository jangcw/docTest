package com.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.CalculatorMcpService;

import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "mcp 접속", description = "mcp 접속 관련 API")
@RestController
public class DirectMcpController {

    private final CalculatorMcpService calculatorMcpService;

    public DirectMcpController(
            CalculatorMcpService calculatorMcpService) {
        this.calculatorMcpService = calculatorMcpService;
    }

    @GetMapping("/multiply")
    public String multiply(
            @RequestParam("a")  double a,
            @RequestParam("b") double b) {

        return calculatorMcpService.multiply(a, b);
    }
}