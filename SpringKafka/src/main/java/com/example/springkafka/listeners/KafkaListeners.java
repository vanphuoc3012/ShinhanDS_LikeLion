package com.example.springkafka.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "likelion", groupId = "group1")
    void listener1(String data) {
        System.out.println("Listener 1");
        System.out.println(data);
    }

    @KafkaListener(topics = "mykafka", groupId = "group1")
    void listener2(String data) {
        System.out.println("Listener 2");
        System.out.println(data);
    }
}
