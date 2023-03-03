package com.example.springwebsocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

import java.time.Duration;

@EnableConfigurationProperties(RedisSessionProperties.class)
@EnableSpringHttpSession
public class SessionConfig {
    @Autowired
    private SessionProperties sessionProperties;
    @Autowired
    private RedisSessionProperties redisSessionProperties;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisOperations<String, Object> sessionRedisOperations() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisSessionRepository sessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
        RedisSessionRepository sessionRepository = new RedisSessionRepository(sessionRedisOperations);
        Duration timeout = this.sessionProperties.getTimeout();
        if(timeout != null) {
            sessionRepository.setDefaultMaxInactiveInterval(timeout);
        }
        sessionRepository.setRedisKeyNamespace(this.redisSessionProperties.getNamespace());
        sessionRepository.setFlushMode(this.redisSessionProperties.getFlushMode());
        sessionRepository.setSaveMode(this.redisSessionProperties.getSaveMode());
        return sessionRepository;
    }
}
