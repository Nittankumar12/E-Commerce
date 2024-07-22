package com.nittan.e_commerce.dto;

import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for representing order response details.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String userEmail;
    private int userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private List<Product> products;
}
