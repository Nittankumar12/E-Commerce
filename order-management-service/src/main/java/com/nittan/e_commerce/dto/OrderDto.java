package com.nittan.e_commerce.dto;

import java.util.List;

public class OrderDto {
    private Integer userId;
    private List<Long> productIds;

    public Integer getUserId() {
        return userId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
