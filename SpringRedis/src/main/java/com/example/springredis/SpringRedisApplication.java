package com.example.springredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
//import springfox.documentation.RequestHandler;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class SpringRedisApplication {
    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    CommandLineRunner commandLineRunner(RedisTemplate<Object, Object> redisTemplate) {
        return args -> {
            redisTemplate.opsForValue().set("likelion", "Hello Redis");
            System.out.println("Value of key likelion: " + redisTemplate.opsForValue().get("likelion"));

            System.out.println("Example operation on List with Redis: ");
            listExample();
        };
    }

    private void listExample() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Phuoc");

        redisTemplate.opsForList().rightPushAll("my_list", list);
        System.out.println("Size of key 'my_list': " + redisTemplate.opsForList().size("my_list"));
    }
}
