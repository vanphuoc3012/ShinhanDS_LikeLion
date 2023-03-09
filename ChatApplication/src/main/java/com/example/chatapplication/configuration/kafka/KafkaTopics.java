package com.example.chatapplication.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {
    public static final String TOPIC1 = "message-topic1";
    public static final String TOPIC2 = "message-topic2";

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(TOPIC1).build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(TOPIC2).build();
    }
}
