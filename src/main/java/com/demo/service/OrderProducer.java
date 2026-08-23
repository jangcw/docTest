package com.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order-created";

    public void sendOrder(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}