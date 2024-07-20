package com.nittan.e_commerce.client;

import com.nittan.e_commerce.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign client interface for interacting with Product Catalog Service.
 */
@FeignClient(name = "PRODUCT-CATALOG-SERVICE")
public interface ProductClient {

    /**
     * Retrieves products for the given list of product IDs.
     * @param productIds List of product IDs.
     * @return ResponseEntity containing a list of products.
     */
    @PostMapping("/product/getProductsForOrder")
    ResponseEntity<List<Product>> getProductsForOrder(@RequestBody List<Long> productIds);

    /**
     * Retrieves all available products from the Product Catalog Service.
     * @return List of products.
     */
    @GetMapping("/product/products")
    List<Product> getAllProducts();
}
