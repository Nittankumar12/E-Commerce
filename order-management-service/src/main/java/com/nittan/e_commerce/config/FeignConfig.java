package com.nittan.e_commerce.config;

import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public FeignContext feignContext() {
        return new FeignContext();
    }
}
