package com.example.springkafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/likelion")
    public void publishTopicLikeLion(@RequestParam(name = "message") String message) {
        kafkaTemplate.send("likelion", message);
    }

    @GetMapping("/mykafka")
    public void publishTopicMyKafka(@RequestParam(name = "message") String message) {
        kafkaTemplate.send("mykafka", message);
    }
}
