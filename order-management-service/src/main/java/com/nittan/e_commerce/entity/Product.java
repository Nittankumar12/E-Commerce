package com.nittan.e_commerce.entity;
//
//import lombok.Data;
//
//@Data
public class Product {
    private String productName;
    private Integer productPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
}
