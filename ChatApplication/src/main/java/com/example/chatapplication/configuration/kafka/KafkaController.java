package com.example.chatapplication.configuration.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaListenerService kafkaListenerService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka")
    public ResponseEntity<?> changeKafkaTopic(@RequestParam String topic) {
        try {
            kafkaListenerService.changeTopic(topic);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Change listener to topic: " + topic);
    }
}
