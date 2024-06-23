package com.nittan.e_commerce.client;

import com.netflix.discovery.converters.Auto;
import com.nittan.e_commerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @PostMapping("/product/getProductsForOrder")
    List<Product> getProductsForOrder(@RequestBody List<Long> productIds);
}
