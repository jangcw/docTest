package com.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("==== [Kafka Consumer] 주문 수신 ====");
        System.out.println("Key   : " + record.key());
        System.out.println("Value : " + record.value());
        System.out.println("===============================");
    }
}