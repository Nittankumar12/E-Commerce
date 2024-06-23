package com.nittan.e_commerce.dto;

import com.nittan.e_commerce.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private Integer userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private List<Product> products;
}
