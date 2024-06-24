//package com.nittan.e_commerce.client;
//
//import com.netflix.discovery.converters.Auto;
//import com.nittan.e_commerce.entity.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//@FeignClient(name = "PRODUCT-SERVICE", url = "${product.service.url}")
//public interface ProductClient {
//
//    @PostMapping("/product/getProductsForOrder")
//    ResponseEntity<List<Product>> getProductsForOrder(@RequestBody List<Long> productIds);
//}


package com.nittan.e_commerce.client;

import com.nittan.e_commerce.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-CATALOG-SERVICE")
public interface ProductClient {

    @PostMapping("/product/getProductsForOrder")
    ResponseEntity<List<Product>> getProductsForOrder(@RequestBody List<Long> productIds);
}
