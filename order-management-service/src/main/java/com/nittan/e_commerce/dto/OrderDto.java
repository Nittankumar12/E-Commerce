package com.nittan.e_commerce.dto;

import java.util.List;

/**
 * DTO for representing order details.
 */
public class OrderDto {
    private Integer userId;
    private List<Long> productIds;

    // Getters and setters for all fields
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
