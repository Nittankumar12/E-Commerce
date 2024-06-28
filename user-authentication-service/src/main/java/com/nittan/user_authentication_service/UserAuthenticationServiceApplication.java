package com.nittan.user_authentication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationServiceApplication.class, args);
	}

}
