package com.nittan.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a RestTemplate bean.
     * @return RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
