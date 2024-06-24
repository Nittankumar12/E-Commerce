package com.nittan.e_commerce.dto;

//import lombok.Data;
//import lombok.Getter;

import java.util.List;

//@Getter
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
