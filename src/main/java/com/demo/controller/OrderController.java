package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.OrderRequest;
import com.demo.service.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Tag(name = "kafka 메시지 처리", description = "메시지 관련 API")
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper;

    @PostMapping("/orders")
    public String createOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        // 주문 정보를 JSON 문자열로 변환
        String message = objectMapper.writeValueAsString(orderRequest);
        // Kafka로 메시지 발행
        orderProducer.sendOrder(message);
        return "주문 생성 & Kafka 발행 완료!";
    }
}