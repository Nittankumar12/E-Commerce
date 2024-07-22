package com.nittan.e_commerce.client;

import com.nittan.e_commerce.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with User authentication service Service.
 */
@FeignClient(name = "USER-AUTHENTICATION-SERVICE")
public interface UserClient {

    /**
     * Get user by id
     * @param userId List of product IDs.
     * @return ResponseEntity for user.
     */
    @GetMapping("/auth/getUserById")
    ResponseEntity<User> getUserById(@RequestParam("id") Integer userId);
}
