package com.naranov.rest_client;

import com.naranov.rest_client.converter.RoleToUserProfileConverter;
//import com.naranov.rest_client.service.MyResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder, MyResponseErrorHandler myResponseErrorHandler) {
//        return builder.errorHandler(myResponseErrorHandler).build();
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    RoleToUserProfileConverter roleToUserProfileConverter;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
