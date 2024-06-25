package com.nittan.e_commerce.dto;

import com.nittan.e_commerce.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
    private Long orderId;
    private Integer userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private List<Product> products;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
