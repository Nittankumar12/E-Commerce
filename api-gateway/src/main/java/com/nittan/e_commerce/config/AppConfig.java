package com.nittan.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // rest template bean
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
