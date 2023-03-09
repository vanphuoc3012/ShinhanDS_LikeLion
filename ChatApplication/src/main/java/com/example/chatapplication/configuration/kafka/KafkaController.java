package com.example.chatapplication.configuration.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {
    @Autowired
    private KafkaListenerService kafkaListenerService;
    @Autowired
    private AdminClient adminClient;

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

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka/create-topic")
    public ResponseEntity<?> createTopic(@RequestParam String topic) {
        adminClient.createTopics(List.of(TopicBuilder.name(topic).build()));
        return ResponseEntity.ok("Successfully create topic: " + topic);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka/delete-topic")
    public ResponseEntity<?> deleteTopic(@RequestParam String topic) {
        adminClient.deleteTopics(List.of(topic));
        return ResponseEntity.ok("Successfully delete topic: " + topic);
    }
}
