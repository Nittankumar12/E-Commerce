package com.nittan.e_commerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Integer userId;
    private List<Long> productIds;
}
